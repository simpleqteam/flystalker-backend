package xyz.simpleq.flystalker

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Mono
//TODO(не импортируется, нада чтобы импортировалось)
//import xyz.simpleq.flystalker.service

@SpringBootTest
class FlystalkerApplicationTests {

    @Test
    fun contextLoads() {
    }

    @Test
    fun sendRequest(){
//
//        val httpRequest = HttpRequest()
//        httpRequest.url ="https://www.google.com/"
//        httpRequest.method = HttpMethod.GET
//
//        val response: Mono<Response> = FlyStalkerHttpClientImpl().sendHttpRequest(httpRequest)
//        response
//                .map(Response::statusCode)
//                .subscribe(System.out::println)

    }

}
