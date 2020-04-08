package xyz.simpleq.flystalker.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import org.springframework.stereotype.Service
import xyz.simpleq.flystalker.model.FSExchange
import xyz.simpleq.flystalker.model.RequestSpec
import xyz.simpleq.flystalker.model.dto.FSExchangeCreationDto
import xyz.simpleq.flystalker.model.from
import xyz.simpleq.flystalker.model.toSpringHttpMethod
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
            headers = objectMapper.valueToTree(exchangeCreationDto.headers),
            queryParams = objectMapper.valueToTree(exchangeCreationDto.queryParams),
            sendAfterDateTime = exchangeCreationDto.sendAfterDateTime
        )

    override fun toModel(exchangeEntity: FSExchangeEntity): FSExchange =
        FSExchange(
            RequestSpec(
                exchangeEntity.id,
                exchangeEntity.creationDateTime,
                method = exchangeEntity.method.toSpringHttpMethod(),
                hostAndPath = exchangeEntity.hostAndPath,
                headers = objectMapper.convertValue(exchangeEntity.headers),
                queryParams = objectMapper.convertValue(exchangeEntity.queryParams),
                sendAfterDateTime = exchangeEntity.sendAfterDateTime
            )
        )
}
