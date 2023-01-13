package com.example.rsockettickermanagementservice

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface TickerCrud : ReactiveMongoRepository<TickerEntity, String> {
    fun findAllByTickerIdNotNull(pageable: Pageable):Flux<TickerEntity>

    fun findByPublisherCompany(company: String): Flux<TickerEntity>

    fun findByExternalReferencesSystemAndExternalReferencesExternalSystemId(system: String,externalSystemId: String): Flux<TickerEntity>



}
