package com.example.scheduleshipmanagementservice

import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface VisitCrud : ReactiveMongoRepository<VisitEntity, String>
{
    fun findAllByShipStatus(shipStatus:String):Flux<VisitEntity>
    fun findAllByShipStatus(shipStatus:String,pageable: PageRequest):Flux<VisitEntity>
    fun countVisitEntityByShipStatus(shipStatus:String):Mono<Int>
    fun countByShipStatusAndTimeInBefore(shipStatus:String, timeIn: String):Mono<Int>
    fun findAllByDock(dockId: String, pageable: PageRequest): Flux<VisitEntity>
    fun findAllByTimeInBetween(from : Date, to: Date, pageable:PageRequest): Flux<VisitEntity>
    fun findAllByShipType(shipType: String, pageable: PageRequest): Flux<VisitEntity>
    fun findAllByShipName(shipName: String, pageable: PageRequest): Flux<VisitEntity>
    fun findAllByShipId(shipId: String,pageable: PageRequest): Flux<VisitEntity>

}