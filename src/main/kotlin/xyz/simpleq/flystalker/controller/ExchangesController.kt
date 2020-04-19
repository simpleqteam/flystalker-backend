package xyz.simpleq.flystalker.controller

import org.springframework.web.bind.annotation.*
import xyz.simpleq.flystalker.model.dto.FSExchangeCreationDto
import xyz.simpleq.flystalker.service.ExchangesStateManager
import java.util.*

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

    @CrossOrigin
    @GetMapping("/exchanges/{id}")
    fun getExchangeInfo(@PathVariable id: UUID) =
            exchangesStateManager
                    .getRequestInfo(id)

    @CrossOrigin
    @GetMapping("/search")
    fun find(@RequestParam pageNumber: Int, @RequestParam pageSize: Int) =
            exchangesStateManager
                    .find(pageNumber, pageSize)

}
