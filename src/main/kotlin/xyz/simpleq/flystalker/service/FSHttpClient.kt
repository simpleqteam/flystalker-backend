package xyz.simpleq.flystalker.service

import reactor.core.publisher.Mono
import org.springframework.web.reactive.function.client.ClientResponse

interface FSHttpClient {
    fun sendHttpRequest(requestSpec: FSRequestSpec) : Mono<ClientResponse>
}