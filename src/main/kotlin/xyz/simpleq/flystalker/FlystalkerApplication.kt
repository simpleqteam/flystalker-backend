package xyz.simpleq.flystalker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FlystalkerApplication

fun main(args: Array<String>) {
    runApplication<FlystalkerApplication>(*args)
}
