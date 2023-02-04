package com.example.scheduleshipmanagementserviceconsumer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class ScheduleShipsController(
    @Autowired val rsocketSchedule: RemoteScheduleShipsService
){

    // TODO: Idan Sorany 
    @RequestMapping(
        path = ["/dock"],
        method = [RequestMethod.POST],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createDock(@RequestBody dock: DockBoundary): Mono<DockBoundary> {
        return this.rsocketSchedule
            .createDock(dock)
    }

    // TODO: Idan Sorany 
    @RequestMapping(
        path = ["/dock"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getAllDocks(
        @RequestParam(name = "sortBy", required = false, defaultValue = "dockId") sortBy:String,
        @RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") sortOrder:String,
        @RequestParam(name = "page", required = false, defaultValue = "0") page:Int,
        @RequestParam(name = "size", required = false, defaultValue = "10") size:Int
    ): Flux<DockBoundary> {
        return this.rsocketSchedule
            .getAllDocks(
                sortBy,
                sortOrder,
                size,
                page
            )
    }

    // TODO: Tor Hanan 
    @RequestMapping(
        path = ["/visit"],
        method = [RequestMethod.POST],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createTicker(@RequestBody visit: VisitBoundary): Mono<VisitBoundary> {
        return this.rsocketSchedule
            .createVisit(visit)
    }

    // TODO: Tor Hanan 
    @RequestMapping(
        path = ["/visit/{visitId}"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getVisitById(@PathVariable visitId: String): Mono<VisitBoundary> {
        return this.rsocketSchedule
            .getSpecificVisit(visitId)
    }

    @RequestMapping(path=["/visit/{visitId}"],
        method = [RequestMethod.PUT],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateVisit (@RequestBody visit: VisitBoundary):Mono<Void>{
        return this.rsocketSchedule
            .update(visit)
    }

    // TODO: Yarden Dahan 
    @RequestMapping(
        path = ["/visit"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun searchUserByAttribute(
        @RequestParam(name = "filterType", required = false, defaultValue = "") filterType:String,
        @RequestParam(name = "filterValue", required = false, defaultValue = "") filterValue:String,
        @RequestParam(name = "from", required = false, defaultValue = "") from: String,
        @RequestParam(name = "to", required = false, defaultValue = "") to: String,
        @RequestParam(name = "sortBy", required = false, defaultValue = "visitId") sortBy:String,
        @RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") sortOrder:String,
        @RequestParam(name = "page", required = false, defaultValue = "0") page:Int,
        @RequestParam(name = "size", required = false, defaultValue = "10") size:Int
    ): Flux<VisitBoundary> {
        return this.rsocketSchedule
            .search(
                filterType,
                filterValue,
                from,
                to,
                sortBy,
                sortOrder,
                size,
                page
            )

    }

    @RequestMapping(
        path = ["/dock"],
        method = [RequestMethod.DELETE])
    fun deleteDocks():Mono<Void>{
        return this.rsocketSchedule
            .deleteDocks()
    }

    @RequestMapping(
        path = ["/visit"],
        method = [RequestMethod.DELETE])
    fun deleteVisits():Mono<Void>{
        return this.rsocketSchedule
            .deleteVisits()
    }
}