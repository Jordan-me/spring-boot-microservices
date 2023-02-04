package com.example.scheduleshipmanagementserviceconsumer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.annotation.PostConstruct

@Service
class RemoteRSocketScheduleService (
    @Autowired val rsocketRequesterBuilder: RSocketRequester.Builder,
    @Value("\${remote.message.rsocket.producer.hostname}") val remoteHost:String,
    @Value("\${remote.message.rsocket.producer.port}") val remotePort:Int
): RemoteScheduleShipsService {
    lateinit var rsocketRequester:RSocketRequester

    @PostConstruct
    fun init(){
        this.rsocketRequester = this.rsocketRequesterBuilder
            .tcp(this.remoteHost, this.remotePort)
    }

    override fun createDock(dock: DockBoundary): Mono<DockBoundary> {
        return this.rsocketRequester
            .route("publish-dock-req-resp")
            .data(dock)
            .retrieveMono(DockBoundary::class.java)
            .log()
    }

    override fun getAllDocks(sortBy: String, sortOrder: String, size: Int, page: Int): Flux<DockBoundary> {
        val paginationData = PaginationBoundary(sortBy,sortOrder, size, page)
        return this.rsocketRequester
            .route("getAllDocks-stream")
            .data(paginationData)
            .retrieveFlux(DockBoundary::class.java)
            .log()
    }

    override fun createVisit(visit: VisitBoundary): Mono<VisitBoundary> {
        return this.rsocketRequester
            .route("publish-visit-req-resp")
            .data(visit)
            .retrieveMono(VisitBoundary::class.java)
            .log()
    }

    override fun update(visit: VisitBoundary): Mono<Void> {
        return this.rsocketRequester
            .route("update-visit-fire-and-forget")
            .data(visit)
            .send()
            .log()
    }

    override fun getSpecificVisit(visitId: String): Mono<VisitBoundary> {
        return this.rsocketRequester
            .route("get-visit-req-resp")
            .data(visitId)
            .retrieveMono(VisitBoundary::class.java)
            .log()
    }

    override fun search(
        filterType: String,
        filterValue: String,
        from: String,
        to: String,
        sortBy: String,
        sortOrder: String,
        size: Int,
        page: Int
    ): Flux<VisitBoundary> {
        val paginationData = PaginationBoundary(filterType, filterValue, sortBy,sortOrder, size, page)
        if(filterType == "byTimeRange")
            paginationData.filterValue = "$from,$to"
        return this.rsocketRequester
            .route("get-visits-req-resp")
            .data(paginationData)
            .retrieveFlux(VisitBoundary::class.java)
            .log()
    }

    override fun deleteDocks(): Mono<Void> {
        return this.rsocketRequester
            .route("deleteAllDocks-fire-and-forget")
            .send()
            .log()
    }

    override fun deleteVisits(): Mono<Void> {
        return this.rsocketRequester
            .route("deleteAllVisits-fire-and-forget")
            .send()
            .log()
    }


}