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
    val headers: List<Header>,
    val queryParams: List<QueryParam>,
    val sendAfterDateTime: OffsetDateTime,
    val body : String?
)

data class ResponseSpec(
        val creationDateTime: OffsetDateTime,
        val headers: List<Header>,
        val statusCode: Int,
        val body: String?
)

data class Header(
    val name: String,
    val value: String
)

data class QueryParam(
    val name: String,
    val values: List<String>
)
