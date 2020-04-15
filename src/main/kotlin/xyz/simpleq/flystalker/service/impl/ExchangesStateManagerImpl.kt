package xyz.simpleq.flystalker.service.impl

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import xyz.simpleq.flystalker.model.FSExchange
import xyz.simpleq.flystalker.model.dto.FSExchangeCreationDto
import xyz.simpleq.flystalker.persistance.entities.FSExchangeEntity
import xyz.simpleq.flystalker.persistance.repositories.ExchangesRepository
import xyz.simpleq.flystalker.service.FSExchangeModelEntityConverter
import xyz.simpleq.flystalker.service.ExchangesStateManager
import java.util.*

@Service

class ExchangesStateManagerImpl(
    private val exchangesRepository: ExchangesRepository,
    private val exchangeModelEntityConverter: FSExchangeModelEntityConverter
) : ExchangesStateManager {
    override fun create(exchangeCreationDto: FSExchangeCreationDto): Mono<FSExchange> =
        Mono.defer {
            val savedExchangeEntity = exchangesRepository.save(
                exchangeCreationDto.toEntity(exchangeModelEntityConverter)
            )
            Mono.just(savedExchangeEntity)
        }
            .map { exchangeModelEntityConverter.toModel(it) }

    override fun getAll(): Flux<FSExchange> =
        Flux.defer { Flux.fromIterable(exchangesRepository.findAll()) }
            .map { it.toModel(exchangeModelEntityConverter) }

    override fun getRequestInfo(uuid: UUID): Mono<FSExchange> =
            Mono.defer{
                Mono.just(exchangesRepository.findById(uuid))
            }
            .map { exchangeModelEntityConverter.toModel(it.get()) }

    override fun getPageByPageInfo(skip: Int, take: Int): Flux<FSExchange> =
            Flux.defer {
                    if(take<1)
                        throw IllegalArgumentException("Error: argument take must be greater than 0 ")

                    val offset:Int = if(skip <= 0) 0 else skip

                    val pageSize = 1
                    val entity:MutableList<FSExchangeEntity> = mutableListOf()
                    for (i in offset until (offset+take)) {
                        entity.addAll(
                                exchangesRepository
                                        .findAll(PageRequest.of(i, pageSize, Sort.by("creationDateTime")))
                                        .content
                        )
                    }
                    Flux.fromIterable(entity)
                }
                        .map { it.toModel(exchangeModelEntityConverter) }

}

private fun FSExchangeCreationDto.toEntity(exchangeModelEntityConverter: FSExchangeModelEntityConverter) =
    exchangeModelEntityConverter.toEntity(this)

private fun FSExchangeEntity.toModel(exchangeModelEntityConverter: FSExchangeModelEntityConverter) =
    exchangeModelEntityConverter.toModel(this)
