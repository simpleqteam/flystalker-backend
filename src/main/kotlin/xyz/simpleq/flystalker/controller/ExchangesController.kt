package xyz.simpleq.flystalker.controller

import org.springframework.web.bind.annotation.*
import xyz.simpleq.flystalker.model.dto.FSExchangeCreationDto
import xyz.simpleq.flystalker.service.ExchangesStateManager

@RestController
@RequestMapping("exchanges")
class ExchangesController(
    private val exchangesStateManager: ExchangesStateManager
) {
    @CrossOrigin
    @GetMapping
    fun getAll() = exchangesStateManager.getAll()

    @CrossOrigin
    @PostMapping
    fun create(@RequestBody exchangeCreationDto: FSExchangeCreationDto) =
        exchangesStateManager
            .create(exchangeCreationDto)
}
