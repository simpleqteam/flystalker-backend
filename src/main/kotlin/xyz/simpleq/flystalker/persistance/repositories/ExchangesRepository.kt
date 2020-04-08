package xyz.simpleq.flystalker.persistance.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import xyz.simpleq.flystalker.persistance.entities.FSExchangeEntity
import java.util.*

@Repository
interface ExchangesRepository : JpaRepository<FSExchangeEntity, UUID>