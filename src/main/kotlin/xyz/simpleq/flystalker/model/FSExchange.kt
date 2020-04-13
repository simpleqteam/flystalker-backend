package xyz.simpleq.flystalker.model

import org.springframework.http.HttpMethod
import java.time.OffsetDateTime
import java.util.*

data class FSExchange(
    val uuid: UUID,
    val requestSpec: RequestSpec,
    val responseDescription: ResponseSpec?
)

data class RequestSpec(
    val creationDateTime: OffsetDateTime,
    val method: HttpMethod,
    val hostAndPath: String,
    val headers: Map<String, String>,
    val queryParams: Map<String, List<String>>,
    val sendAfterDateTime: OffsetDateTime,
    val body : String?
)

data class ResponseSpec(
        val creationDateTime: OffsetDateTime,
        val headers: Map<String, String>,
        val statusCode: Int,
        val body: String?
)
