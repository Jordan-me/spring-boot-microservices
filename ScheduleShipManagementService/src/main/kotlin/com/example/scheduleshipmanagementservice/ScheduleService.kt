package com.example.scheduleshipmanagementservice

import reactor.core.publisher.Mono

interface ScheduleService {

    fun deleteVisits(): Mono<Void>

    fun deleteDockers(): Mono<Void>

}
