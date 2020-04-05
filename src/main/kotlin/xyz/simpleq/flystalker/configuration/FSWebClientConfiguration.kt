package xyz.simpleq.flystalker.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@EnableConfigurationProperties(FSWebClientProperties::class)
class FSWebClientConfiguration(
    private val webClientBuilder: WebClient.Builder,
    private val fsWebClientProperties: FSWebClientProperties
) {
    @Bean
    fun fsWebClient() =
        webClientBuilder
            .clone()
            .defaultHeaders { headers ->
                fsWebClientProperties.defaultHeaders.forEach { headers[it.key] = it.value }
            }
            .build()
}

@ConstructorBinding
@ConfigurationProperties("fs-web-client")
data class FSWebClientProperties(
    val defaultHeaders: Map<String, String> = emptyMap()
)
