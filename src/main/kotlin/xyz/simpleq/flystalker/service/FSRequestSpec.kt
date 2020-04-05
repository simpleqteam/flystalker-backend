package xyz.simpleq.flystalker.service

import org.springframework.http.HttpMethod
import java.time.Duration

data class FSRequestSpec(
    val method: HttpMethod = HttpMethod.GET,
    val uri: String,
    val headers: Map<String, String> = emptyMap(),
    val queryParams: Map<String, List<String>> = mapOf(),
    val delayFor: Duration = Duration.ZERO
)
