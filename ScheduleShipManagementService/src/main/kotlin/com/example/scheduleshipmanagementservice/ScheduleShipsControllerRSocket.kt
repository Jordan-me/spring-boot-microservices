package com.example.scheduleshipmanagementservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class ScheduleShipsControllerRSocket(
    @Autowired val schduleService: ScheduleService
    ){

    @MessageMapping("publish-dock-req-resp")
    fun create (dock:DockBoundary): Mono<DockBoundary> {
        return this.schduleService.create(dock)
    }

    @MessageMapping("getAllDocks-stream")
    fun getDocks(paginationData: PaginationBoundary): Flux<DockBoundary> {
        val pageable = PageRequest.of(
            paginationData.page,
            paginationData.size,
            this.schduleService.getSortOrder(paginationData.sortOrder!!),
            this.schduleService.getSortBy(paginationData.sortBy!!),
            )
        return this.schduleService
            .getDocks(pageable)
    }

    @MessageMapping("get-visits-req-resp")
    fun getVisits(paginationData: PaginationBoundary): Flux<VisitBoundary> {
        val pageable = PageRequest.of(
            paginationData.page,
            paginationData.size,
            this.schduleService.getSortOrder(paginationData.sortOrder!!),
            this.schduleService.getSortBy(paginationData.sortBy!!),
            "visitId"
            )
        return this.schduleService
            .getVisits(paginationData.filterType!!, paginationData.filterValue!!,pageable)
    }
    @MessageMapping("update-visit-fire-and-forget")
    fun updateVisit(visit: VisitBoundary): Mono<Void> {
        return this.schduleService
            .update(visit)
    }
    @MessageMapping("deleteAllDocks-fire-and-forget")
    fun deleteDocks(): Mono<Void> {
        return schduleService.deleteDocks()
    }
    @MessageMapping("deleteAllVisits-fire-and-forget")
    fun deleteVisits(): Mono<Void> {
        return schduleService.deleteVisits()
    }

    @MessageMapping("get-visit-req-resp")
    fun getByIds(ids: Flux<IdBoundary>): Flux<VisitBoundary> {
        return ids
            .map { it.id!! }
            .flatMap { this.schduleService
                .getSpecificVisit(it)}
            .log()
    }

    @MessageMapping("publish-visit-req-resp")
    fun create (visit:VisitBoundary): Mono<VisitBoundary> {
        return this.schduleService.createVisit(visit)
    }

}