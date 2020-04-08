package xyz.simpleq.flystalker.model.dto

import org.springframework.http.HttpMethod
import java.time.OffsetDateTime
import java.time.ZoneOffset

data class FSExchangeCreationDto(
    val method: HttpMethod,
    val hostAndPath: String,
    val headers: Map<String, String> = emptyMap(),
    val queryParams: Map<String, List<String>> = mapOf(),
    val sendAfterDateTime: OffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC)
)
