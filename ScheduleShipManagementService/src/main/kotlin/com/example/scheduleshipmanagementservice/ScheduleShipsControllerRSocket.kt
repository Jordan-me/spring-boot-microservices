package com.example.scheduleshipmanagementservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono

@Controller
class ScheduleShipsControllerRSocket(
    @Autowired val schduleService: ScheduleService
    ){

    @MessageMapping("deleteAllDockers-fire-and-forget")
    fun deleteDockers(): Mono<Void> {
        return schduleService.deleteDockers()
    }
    @MessageMapping("deleteAllVisits-fire-and-forget")
    fun deleteVisits(): Mono<Void> {
        return schduleService.deleteVisits()
    }

}