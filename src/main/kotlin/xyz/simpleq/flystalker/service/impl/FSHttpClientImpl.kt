package xyz.simpleq.flystalker.service.impl

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import xyz.simpleq.flystalker.service.FSHttpClient
import xyz.simpleq.flystalker.service.FSRequestSpec
import java.net.URI

@Service
class FSHttpClientImpl(
    private val fsWebClient: WebClient
) : FSHttpClient {
    override fun sendHttpRequest(requestSpec: FSRequestSpec): Mono<ClientResponse> =
        fsWebClient
            .method(requestSpec.method)
            .uri { uriBuilder ->
                val uri = URI(requestSpec.uri)
                uriBuilder
                    .scheme(uri.scheme)
                    .fragment(uri.fragment)
                    .userInfo(uri.userInfo)
                    .host(uri.host)
                    .port(uri.port)
                    .path(uri.path)
                    .apply {
                        requestSpec.queryParams.forEach { queryParam(it.key, it.value) }
                    }
                    .build()
            }
            .headers { headers ->
                requestSpec.headers.forEach { headers[it.key] = it.value }
            }
            .exchange()
            .delaySubscription(requestSpec.delayFor)
}
