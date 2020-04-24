package xyz.simpleq.flystalker.persistance.entities

import com.fasterxml.jackson.databind.JsonNode
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id


@Entity
@TypeDef(name = "jsonb", typeClass = JsonNodeBinaryType::class)
data class FSExchangeEntity(
    @Id
    val id: UUID = UUID.randomUUID(),

    val creationDateTime: OffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC),

    val method: HttpMethod,

    val hostAndPath: String,

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    val queryParams: JsonNode,

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    val requestHeaders: JsonNode,

    val requestBody: String?,

    var responseBody: String?,

    var responseStatusCode: Int?,

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    var responseHeaders: JsonNode?,

    val sendAfterDateTime: OffsetDateTime
) {
    enum class HttpMethod {
        GET, POST, PUT, DELETE, HEAD, OPTIONS;

        companion object
    }

    companion object
}
