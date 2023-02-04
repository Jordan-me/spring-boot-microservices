package com.example.scheduleshipmanagementservice

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ScheduleService {

    fun getSortOrder(sortOrder: String): Sort.Direction
    fun getSortBy(sortBy: String): String

    // Visit
    fun deleteVisits(): Mono<Void>
    fun update(visit: VisitBoundary): Mono<Void>
    fun getVisits(filterType: String, filterValue: String, pageable: PageRequest): Flux<VisitBoundary>


    // Dock
    fun create(dock: DockBoundary): Mono<DockBoundary>
    fun deleteDocks(): Mono<Void>
    fun getDocks(filterType: String, filterValue: String, pageable: PageRequest): Flux<DockBoundary>


}
