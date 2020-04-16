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

    override fun find(pageNumber: Int, pageSize: Int): Flux<FSExchange> {
        return validateLowerThresholdNumber(pageNumber, 0,
                "Error: argument pageNumber must be greater than 0 ").thenMany(
                    validateLowerThresholdNumber(pageSize, 1,
                    "Error: argument pageSize must be greater than 1 ").thenMany(
                        Flux.fromIterable(exchangesRepository
                                .findAll(PageRequest.of(pageNumber, pageSize, Sort.by("creationDateTime")))
                                .content)
                            .map { it.toModel(exchangeModelEntityConverter) }
                    )
        )
    }

}

private fun validateLowerThresholdNumber(number: Int, threshold:Int, explanation: String): Mono<Void> = when {
    number < threshold -> Mono.error(IllegalArgumentException(explanation))
    else -> Mono.empty()

}

private fun FSExchangeCreationDto.toEntity(exchangeModelEntityConverter: FSExchangeModelEntityConverter) =
    exchangeModelEntityConverter.toEntity(this)

private fun FSExchangeEntity.toModel(exchangeModelEntityConverter: FSExchangeModelEntityConverter) =
    exchangeModelEntityConverter.toModel(this)
