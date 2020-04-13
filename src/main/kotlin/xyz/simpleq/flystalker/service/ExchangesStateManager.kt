package xyz.simpleq.flystalker.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import xyz.simpleq.flystalker.model.FSExchange
import xyz.simpleq.flystalker.model.dto.FSExchangeCreationDto
import java.util.*

interface ExchangesStateManager {
    fun create(exchangeCreationDto: FSExchangeCreationDto): Mono<FSExchange>
    fun getAll(): Flux<FSExchange>
    fun getRequestInfo(uuid: UUID): Mono<FSExchange>
}
