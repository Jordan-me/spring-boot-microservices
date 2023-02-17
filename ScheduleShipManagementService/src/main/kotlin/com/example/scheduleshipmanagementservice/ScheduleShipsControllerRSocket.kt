package com.example.scheduleshipmanagementservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class ScheduleShipsControllerRSocket(
    @Autowired val scheduleService: ScheduleService
    ){

    @MessageMapping("publish-dock-req-resp")
    fun create (dock:DockBoundary): Mono<DockBoundary> {
        return this.scheduleService.createDock(dock)
    }

    @MessageMapping("get-docks-req-stream")
    fun getDocks(paginationData: PaginationBoundary): Flux<DockBoundary> {
        val pageable = PageRequest.of(
            paginationData.page,
            paginationData.size,
            this.scheduleService.getSortOrder(paginationData.sortOrder!!),
            this.scheduleService.getSortBy(paginationData.sortBy!!, true),
            )
        return this.scheduleService
            .getDocks(pageable)
    }

    @MessageMapping("get-visits-req-stream")
    fun getVisits(paginationData: PaginationBoundary): Flux<VisitBoundary> {
        val sortBy = this.scheduleService.getSortBy(paginationData.sortBy!!, false)
//        val sortOrder = this.scheduleService.getSortOrder(paginationData.sortOrder!!)
//        val sort = Sort.by(sortOrder, sortBy)
//        val pageable = PageRequest.of(paginationData.page,paginationData.size, sort)
        val pageable = PageRequest.of(
            paginationData.page,
            paginationData.size,
            this.scheduleService.getSortOrder(paginationData.sortOrder!!),
            this.scheduleService.getSortBy(paginationData.sortBy!!, false),
        )
//        val pageable = PageRequest.of(
//            paginationData.page,
//            paginationData.size,
//            this.scheduleService.getSortOrder(paginationData.sortOrder!!),
//            this.scheduleService.getSortBy(paginationData.sortBy!!, false),
//            "id"
//            )
        return this.scheduleService
            .getVisits(paginationData.filterType!!, paginationData.filterValue!!,pageable)
    }
    @MessageMapping("update-visit-fire-and-forget")
    fun updateVisit(visit: VisitBoundary): Mono<Void> {
        return this.scheduleService
            .update(visit)
    }
    @MessageMapping("deleteAllDocks-fire-and-forget")
    fun deleteDocks(): Mono<Void> {
        return scheduleService.deleteDocks()
    }
    @MessageMapping("deleteAllVisits-fire-and-forget")
    fun deleteVisits(): Mono<Void> {
        return scheduleService.deleteVisits()
    }

    @MessageMapping("get-visit-req-resp")
    fun getVisitById(visitId: String): Mono<VisitBoundary> {
        return scheduleService.getSpecificVisit(visitId)
    }

    @MessageMapping("publish-visit-req-resp")
    fun create (visit:VisitBoundary): Mono<VisitBoundary> {
        return this.scheduleService.createVisit(visit)
    }

}