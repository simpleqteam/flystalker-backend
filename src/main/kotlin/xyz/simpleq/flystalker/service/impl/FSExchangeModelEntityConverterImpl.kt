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
            responseBody = "",
            responseHeaders = objectMapper.convertValue(mapOf<String, List<String>>()),
            responseStatusCode = null
        )

    override fun toModel(exchangeEntity: FSExchangeEntity): FSExchange =
        FSExchange(
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
            ResponseSpec(
                exchangeEntity.creationDateTime,
                headers = objectMapper.convertValue(exchangeEntity.responseHeaders),
                statusCode = exchangeEntity.responseStatusCode,
                body = exchangeEntity.responseBody
            )
        )
}
