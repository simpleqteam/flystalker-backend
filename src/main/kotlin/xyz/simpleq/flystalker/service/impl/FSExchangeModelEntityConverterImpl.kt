package xyz.simpleq.flystalker.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import org.springframework.stereotype.Service
import xyz.simpleq.flystalker.model.*
import xyz.simpleq.flystalker.model.dto.FSExchangeCreationDto
import xyz.simpleq.flystalker.persistance.entities.FSExchangeEntity
import xyz.simpleq.flystalker.service.FSExchangeModelEntityConverter

@Service
class FSExchangeModelEntityConverterImpl(
    private val objectMapper: ObjectMapper
) : FSExchangeModelEntityConverter {
    override fun toEntity(exchangeCreationDto: FSExchangeCreationDto): FSExchangeEntity =
        FSExchangeEntity(
            method = FSExchangeEntity.HttpMethod.from(exchangeCreationDto.method),
            hostAndPath = exchangeCreationDto.hostAndPath,
            requestHeaders = objectMapper.valueToTree(exchangeCreationDto.headers),
            queryParams = objectMapper.valueToTree(exchangeCreationDto.queryParams),
            sendAfterDateTime = exchangeCreationDto.sendAfterDateTime,
            requestBody = exchangeCreationDto.body,
            responseBody = null,
            responseHeaders = null,
            responseStatusCode = null
        )

    override fun toEntity(exchange: FSExchange): FSExchangeEntity =
        FSExchangeEntity(
            method = FSExchangeEntity.HttpMethod.from(exchange.requestSpec.method),
            hostAndPath = exchange.requestSpec.hostAndPath,
            requestHeaders = objectMapper.valueToTree(exchange.requestSpec.headers),
            queryParams = objectMapper.valueToTree(exchange.requestSpec.queryParams),
            requestBody = exchange.requestSpec.body,
            sendAfterDateTime = exchange.requestSpec.sendAfterDateTime,
            responseBody = exchange.responseDescription?.body,
            responseHeaders = objectMapper.valueToTree(exchange.responseDescription?.headers),
            responseStatusCode = exchange.responseDescription?.statusCode
        )

    override fun toModel(exchangeEntity: FSExchangeEntity): FSExchange {
        val responseHeaders = exchangeEntity.responseHeaders
        val statusCode = exchangeEntity.responseStatusCode
        return FSExchange(
            exchangeEntity.id,
            RequestSpec(
                exchangeEntity.creationDateTime,
                method = exchangeEntity.method.toSpringHttpMethod(),
                hostAndPath = exchangeEntity.hostAndPath,
                headers = objectMapper.convertValue(exchangeEntity.requestHeaders),
                queryParams = objectMapper.convertValue(exchangeEntity.queryParams),
                sendAfterDateTime = exchangeEntity.sendAfterDateTime,
                body = exchangeEntity.requestBody
            ),
            if (responseHeaders == null || statusCode == null) {
                null
            } else {
                ResponseSpec(
                    exchangeEntity.creationDateTime,
                    headers = objectMapper.convertValue(responseHeaders),
                    statusCode = statusCode,
                    body = exchangeEntity.responseBody
                )
            }
        )
    }
}
