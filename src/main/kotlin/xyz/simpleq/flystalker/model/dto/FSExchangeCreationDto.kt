package xyz.simpleq.flystalker.model.dto

import org.springframework.http.HttpMethod
import xyz.simpleq.flystalker.model.Header
import xyz.simpleq.flystalker.model.QueryParam
import java.time.OffsetDateTime
import java.time.ZoneOffset

data class FSExchangeCreationDto(
    val method: HttpMethod,
    val hostAndPath: String,
    val headers: List<Header> = emptyList(),
    val queryParams: List<QueryParam> = emptyList(),
    val body: String? = null,
    val sendAfterDateTime: OffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC)
)
