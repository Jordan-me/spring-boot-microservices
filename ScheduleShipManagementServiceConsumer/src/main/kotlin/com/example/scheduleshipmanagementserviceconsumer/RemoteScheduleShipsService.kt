package com.example.scheduleshipmanagementserviceconsumer

import com.example.scheduleshipmanagementservice.VisitBoundary
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface RemoteScheduleShipsService {
    fun createDocker(docker: DockerBoundary): Mono<DockerBoundary>

    fun getAllDocks(sortAttribute: String, sortOrder: String, size: Int, page: Int): Flux<DockerBoundary>

    fun createVisit(visit: VisitBoundary): Mono<VisitBoundary>

    fun update(visit: VisitBoundary): Mono<Void>

    fun getSpecificVisit(visitId: String): Mono<VisitBoundary>

    fun search(
        filterType: String,
        filterValue: String,
        from: String,
        to: String,
        sortAttribute: String,
        sortOrder: String,
        size: Int,
        page: Int
    ): Flux<VisitBoundary>

    fun deleteDockers(): Mono<Void>

    fun deleteVisits(): Mono<Void>


}
