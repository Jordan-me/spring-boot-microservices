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

    @MessageMapping("publish-dock-ticker-req-resp")
    fun create (dock:DockBoundary): Mono<DockBoundary> {
        return this.schduleService.create(dock)
    }

    @MessageMapping("get-visits-req-resp")
    fun getVisits(paginationData: PaginationBoundary): Flux<VisitBoundary> {
        val pageable = PageRequest.of(
            paginationData.page,
            paginationData.size,
            this.schduleService.getSortOrder(paginationData.sortOrder!!),
            this.schduleService.getSortAttribute(paginationData.sortAttribute!!),
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
    @MessageMapping("deleteAllDockers-fire-and-forget")
    fun deleteDockers(): Mono<Void> {
        return schduleService.deleteDockers()
    }
    @MessageMapping("deleteAllVisits-fire-and-forget")
    fun deleteVisits(): Mono<Void> {
        return schduleService.deleteVisits()
    }

}