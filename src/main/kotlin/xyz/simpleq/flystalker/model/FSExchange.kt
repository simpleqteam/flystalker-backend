package xyz.simpleq.flystalker.model

import org.springframework.http.HttpMethod
import java.time.OffsetDateTime
import java.util.*

data class FSExchange(
    val requestSpec: RequestSpec
)

data class RequestSpec(
    val uuid: UUID,
    val creationDateTime: OffsetDateTime,
    val method: HttpMethod,
    val hostAndPath: String,
    val headers: Map<String, String>,
    val queryParams: Map<String, List<String>>,
    val sendAfterDateTime: OffsetDateTime
)
