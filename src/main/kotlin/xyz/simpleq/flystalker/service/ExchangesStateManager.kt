package xyz.simpleq.flystalker.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import xyz.simpleq.flystalker.model.FSExchange
import xyz.simpleq.flystalker.model.dto.FSExchangeCreationDto
import java.util.*

interface ExchangesStateManager {
    fun create(exchangeCreationDto: FSExchangeCreationDto): Mono<FSExchange>
    fun getRequestInfo(uuid: UUID): Mono<FSExchange>
    fun find(pageNumber: Int, pageSize: Int): Flux<FSExchange>
    fun countExchanges(): Mono<Long>
}
