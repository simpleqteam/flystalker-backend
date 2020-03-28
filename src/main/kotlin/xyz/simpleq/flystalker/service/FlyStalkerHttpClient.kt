package xyz.simpleq.flystalker.service

import reactor.core.publisher.Mono
import khttp.responses.Response

interface FlyStalkerHttpClient {
    fun sendHttpRequest(request: HttpRequest) : Mono<Response>
}