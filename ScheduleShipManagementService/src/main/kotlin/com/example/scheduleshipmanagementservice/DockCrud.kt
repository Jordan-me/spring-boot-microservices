package com.example.scheduleshipmanagementservice

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface DockCrud : ReactiveMongoRepository<DockEntity, String>{
    fun findByDockIdAndTakenByIsNull(dockId:String): Mono<DockEntity>
    fun findByTakenByIsNull(): Flux<DockEntity>
}