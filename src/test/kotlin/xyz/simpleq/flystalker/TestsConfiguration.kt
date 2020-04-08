package xyz.simpleq.flystalker

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@TestConfiguration
class TestsConfiguration {
    @Bean
    @Profile("test")
    fun dataSource(): DataSource = DriverManagerDataSource().apply {
        setDriverClassName("org.h2.Driver")
        url = "jdbc:h2:mem:db"
        username = "flystalker-user"
        password = "flystalker-password"
    }
}
