package xyz.simpleq.flystalker

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus

@TestConfiguration
@EnableConfigurationProperties(MockWebServerProperties::class)
class MockWebServerConfiguration(
    private val mockWebServerProperties: MockWebServerProperties
) {
    @Bean
    fun mockWebServer() = MockWebServer().apply {
        dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse =
                MockResponse()
                    .setResponseCode(HttpStatus.OK.value())
        }
        start(mockWebServerProperties.testPort)
    }
}

@ConstructorBinding
@ConfigurationProperties("mock-web-server")
data class MockWebServerProperties(
    val testPort: Int = 8081
) {
    val url: String
        get() = "http://localhost:${testPort}"
}
