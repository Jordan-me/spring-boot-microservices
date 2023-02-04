package com.example.scheduleshipmanagementserviceconsumer

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface RemoteScheduleShipsService {
    fun createDock(dock: DockBoundary): Mono<DockBoundary>

    fun getAllDocks(sortBy: String, sortOrder: String, size: Int, page: Int): Flux<DockBoundary>

    fun createVisit(visit: VisitBoundary): Mono<VisitBoundary>

    fun update(visit: VisitBoundary): Mono<Void>

    fun getSpecificVisit(visitId: String): Mono<VisitBoundary>

    fun search(
        filterType: String,
        filterValue: String,
        from: String,
        to: String,
        sortBy: String,
        sortOrder: String,
        size: Int,
        page: Int
    ): Flux<VisitBoundary>

    fun deleteDocks(): Mono<Void>

    fun deleteVisits(): Mono<Void>


}
