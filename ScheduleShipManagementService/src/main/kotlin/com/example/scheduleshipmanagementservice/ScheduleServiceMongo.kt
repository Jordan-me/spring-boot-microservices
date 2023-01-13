package com.example.scheduleshipmanagementservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ScheduleServiceMongo (
    @Autowired    val dockerCrud: DockerCrud,
    @Autowired    val visitCrud: VisitCrud,
    @Autowired    val converter: ScheduleShipsConverter
): ScheduleService {

    override fun deleteVisits(): Mono<Void> {
        return this.visitCrud.deleteAll()
    }

    override fun deleteDockers(): Mono<Void> {
        return this.dockerCrud.deleteAll()
    }


}