package xyz.simpleq.flystalker.model

import org.springframework.http.HttpMethod
import xyz.simpleq.flystalker.persistance.entities.FSExchangeEntity

fun FSExchangeEntity.HttpMethod.Companion.from(httpMethod: HttpMethod) =
    FSExchangeEntity.HttpMethod.values()
        .find { it.name == httpMethod.name }
        ?: throw IllegalArgumentException("Error converting http method $httpMethod to entity HttpMethod")

fun FSExchangeEntity.HttpMethod.toSpringHttpMethod() =
    HttpMethod.values()
        .find { it.name == this.name }
        ?: throw IllegalArgumentException("Error converting entity http method $this to HttpMethod")
