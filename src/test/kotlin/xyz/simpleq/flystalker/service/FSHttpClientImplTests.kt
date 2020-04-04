package xyz.simpleq.flystalker.service

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import reactor.test.StepVerifier
import xyz.simpleq.flystalker.MockWebServerConfiguration
import xyz.simpleq.flystalker.MockWebServerProperties
import xyz.simpleq.flystalker.configuration.FSWebClientConfiguration
import xyz.simpleq.flystalker.configuration.FSWebClientProperties
import xyz.simpleq.flystalker.service.impl.FSHttpClientImpl
import xyz.simpleq.flystalker.takeRequestOrFail
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit
import java.util.concurrent.TimeUnit

@SpringBootTest(
    classes = [
        FSHttpClientImpl::class,
        FSWebClientConfiguration::class,
        WebClientAutoConfiguration::class,
        MockWebServerConfiguration::class
    ]
)
class FSHttpClientImplTests {
    @Autowired
    lateinit var fsHttpClient: FSHttpClient

    @Autowired
    lateinit var mockWebServer: MockWebServer

    @Autowired
    lateinit var mockWebServerProperties: MockWebServerProperties

    @Autowired
    lateinit var fsWebClientProperties: FSWebClientProperties

    @Test
    fun `sends correct request content`() {
        val requestUriString = mockWebServerProperties.url
        val requestHeaders = mapOf(
            "Content-Type" to "application/json",
            "X-Test-Header" to "x-test-header value"
        )
        val requestQueryParams = mapOf(
            "param-one" to listOf("val-1"),
            "param-two" to listOf("val-2-1", "val-2-2")
        )


        val responseMono = fsHttpClient
            .sendHttpRequest(FSRequestSpec(
                method = HttpMethod.GET,
                uri = requestUriString,
                headers = requestHeaders,
                queryParams = requestQueryParams
            ))

        StepVerifier
            .create(responseMono)
            .expectNextCount(1)
            .verifyComplete()

        with(mockWebServer.takeRequestOrFail()) {
            assertAll(
                { assertThat(method).isEqualTo(HttpMethod.GET.name) },
                { assertThat(requestUrl).isNotNull },
                { assertThat(requestUrl!!.toString()).startsWith(requestUriString) },
                { assertThat(requestUrl!!.queryParameterNames).isEqualTo(requestQueryParams.keys) },
                {
                    assertAll(requestQueryParams
                        .map { ({ assertThat(requestUrl!!.queryParameterValues(it.key)).isEqualTo(it.value); Unit }) }
                    )
                },
                { assertAll(requestHeaders.map { { assertThat(headers[it.key]).isEqualTo(it.value); Unit } }) }
            )
        }
    }

    @Test
    fun `adds correct extra request headers`() {
        val responseMono = fsHttpClient
            .sendHttpRequest(FSRequestSpec(
                method = HttpMethod.GET,
                uri = mockWebServerProperties.url
            ))

        StepVerifier
            .create(responseMono)
            .expectNextCount(1)
            .verifyComplete()

        // default headers + "Host"
        val expectedHeaderCount = fsWebClientProperties.defaultHeaders.size + 1
        val expectedHostValue = mockWebServerProperties.url.toHttpUrl().let { "${it.host}:${it.port}" }
        with(mockWebServer.takeRequestOrFail()) {
            assertAll(
                { assertThat(headers.size).isEqualTo(expectedHeaderCount) },
                {
                    assertAll(fsWebClientProperties.defaultHeaders
                        .map { { assertThat(headers[it.key]).isEqualTo(it.value); Unit } }
                    )
                },
                { assertThat(headers["Host"]).isEqualTo(expectedHostValue) }
            )
        }
    }

    @Test
    fun `delays requests correctly`() {
        val delayDuration = Duration.of(5, ChronoUnit.SECONDS)

        StepVerifier
            .withVirtualTime {
                fsHttpClient
                    .sendHttpRequest(FSRequestSpec(
                        method = HttpMethod.GET,
                        uri = mockWebServerProperties.url,
                        delayFor = delayDuration
                    ))
            }
            .expectSubscription()
            .expectNoEvent(delayDuration)
            .expectNextCount(1)
            .verifyComplete()

        assertThat(mockWebServer.takeRequestOrFail()).isNotNull
    }
}
