package xyz.simpleq.flystalker.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("requests")
class RequestsController {
    @GetMapping
    fun getAll() = Mono.empty<Void>()

    @PostMapping
    fun create() = Mono.empty<Void>()
}
