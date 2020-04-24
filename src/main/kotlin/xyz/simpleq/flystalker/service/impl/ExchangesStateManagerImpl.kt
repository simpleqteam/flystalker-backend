package xyz.simpleq.flystalker.service.impl

import kotlinx.coroutines.reactive.awaitLast
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import xyz.simpleq.flystalker.model.FSExchange
import xyz.simpleq.flystalker.model.Header
import xyz.simpleq.flystalker.model.ResponseSpec
import xyz.simpleq.flystalker.model.dto.FSExchangeCreationDto
import xyz.simpleq.flystalker.persistance.entities.FSExchangeEntity
import xyz.simpleq.flystalker.persistance.repositories.ExchangesRepository
import xyz.simpleq.flystalker.service.FSExchangeModelEntityConverter
import xyz.simpleq.flystalker.service.ExchangesStateManager
import java.time.OffsetDateTime
import java.time.ZoneOffset
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

    override fun getRequestInfo(uuid: UUID): Mono<FSExchange> =
        Mono.defer {
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

    override fun countExchanges(): Mono<Long> =
        Mono.defer {
            Mono.just(exchangesRepository.count())
        }

    override fun saveResponseDescription(fsExchange: FSExchange, response: ClientResponse) =
        response.bodyToMono<String>()
            .map { body ->
                val responseSpec = ResponseSpec(
                    creationDateTime = OffsetDateTime.now(ZoneOffset.UTC),
                    statusCode = response.rawStatusCode(),
                    headers = response.headers().asHttpHeaders()
                        .map { Header(it.key, it.value.first()) },
                    body = body
                )
                fsExchange.responseDescription = responseSpec
                exchangesRepository.save(fsExchange.toEntity(exchangeModelEntityConverter))
            }
            .then()
}

private fun validateLowerThresholdNumber(number: Int, threshold: Int, explanation: String): Mono<Void> = when {
    number < threshold -> Mono.error(IllegalArgumentException(explanation))
    else -> Mono.empty()

}

private fun FSExchangeCreationDto.toEntity(exchangeModelEntityConverter: FSExchangeModelEntityConverter) =
    exchangeModelEntityConverter.toEntity(this)

private fun FSExchange.toEntity(exchageModelEntityConverter: FSExchangeModelEntityConverter) =
    exchageModelEntityConverter.toEntity(this)

private fun FSExchangeEntity.toModel(exchangeModelEntityConverter: FSExchangeModelEntityConverter) =
    exchangeModelEntityConverter.toModel(this)
