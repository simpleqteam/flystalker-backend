package xyz.simpleq.flystalker.service

import reactor.core.publisher.Mono
import org.springframework.web.reactive.function.client.ClientResponse
import xyz.simpleq.flystalker.model.FSExchange
import xyz.simpleq.flystalker.model.RequestSpec

interface FSHttpClient {
    fun sendHttpRequest(requestSpec: RequestSpec) : Mono<ClientResponse>
}