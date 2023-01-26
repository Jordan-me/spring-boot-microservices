package com.example.scheduleshipmanagementservice

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ScheduleService {

    fun deleteVisits(): Mono<Void>
    fun deleteDockers(): Mono<Void>
    fun update(visit: VisitBoundary): Mono<Void>
    fun getVisits(filterType: String, filterValue: String, pageable: PageRequest): Flux<VisitBoundary>
    fun getSortOrder(sortOrder: String): Sort.Direction
    fun getSortAttribute(sortAttribute: String): String

}
