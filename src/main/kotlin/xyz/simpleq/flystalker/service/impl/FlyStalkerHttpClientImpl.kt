package xyz.simpleq.flystalker.service.impl

import org.springframework.stereotype.Service
import xyz.simpleq.flystalker.service.FlyStalkerHttpClient
import xyz.simpleq.flystalker.service.HttpRequest
import khttp.responses.Response
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class FlyStalkerHttpClientImpl : FlyStalkerHttpClient {
    override fun sendHttpRequest(request: HttpRequest) : Mono<Response> {
        return Mono.just(
                khttp.request(request.method.name, request.url, request.headers, request.params)
        ).delaySubscription(Duration.ofSeconds(request.time))
    }

}