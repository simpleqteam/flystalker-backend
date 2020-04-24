package xyz.simpleq.flystalker.service

import xyz.simpleq.flystalker.model.FSExchange
import xyz.simpleq.flystalker.model.dto.FSExchangeCreationDto
import xyz.simpleq.flystalker.persistance.entities.FSExchangeEntity

interface FSExchangeModelEntityConverter {
    fun toEntity(exchangeCreationDto: FSExchangeCreationDto): FSExchangeEntity
    fun toEntity(exchange: FSExchange): FSExchangeEntity
    fun toModel(exchangeEntity: FSExchangeEntity): FSExchange
}
