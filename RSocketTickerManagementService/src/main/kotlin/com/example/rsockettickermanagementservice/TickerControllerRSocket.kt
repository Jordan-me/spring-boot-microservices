package com.example.rsockettickermanagementservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Controller
class TickerControllerRSocket(
    @Autowired val tickerService: TickerService
    ){

//     java -jar rsc-0.9.1.jar --debug --request --data "{\"tickerId\":\"1a2bc3d4e5f6\",\"publisher\":{\"company\":\"SFO Port Authority\",\"email\":\"jane@sfport.com\"},\"publishedTimestamp\":\"2022-12-22T16:13:57.190+0000\",\"tickerType\":\"generalNotification\",\"summary\":\"Port services function as usual through the holidays season\",\"externalReferences\":[{\"system\":\"SFONotifications\",\"externalSystemId\":\"2022-DEC-22-MSG-5396\"}],\"relatedTickerIds\":[\"1a2bc3d4e592\", \"1a2bc3d42aab\"],\"tickerDetails\":{\"category\":\"General Port Notifications\",\"status\":\"NOTIFIED\"}}" --route publish-ticker-req-resp tcp://localhost:7000
    @MessageMapping("publish-ticker-req-resp")
    fun create (ticker:TickerBoundary): Mono<TickerBoundary> {
        return this.tickerService.create(ticker)
    }

//     java -jar rsc-0.9.1.jar --debug --fnf --route bind-tickers-fire-and-forget tcp://localhost:7000
    @MessageMapping("bind-tickers-fire-and-forget")
    fun bindTickers(request: TickerBindBoundary): Mono<Void> {
        return this.tickerService
            .bindTickers(request.tickerId!!, request.relatedTickerIds!!)
    }

//     java -jar rsc-0.9.1.jar --debug --stream --data "{\"tickerId\":\"1a2bc3d4e5f6\",\"relatedTickerIds\":[\"1a2bc3d4e592", \"1a2bc3d42aab\"]}" --route getAllTickers-stream tcp://localhost:7000
    @MessageMapping("getAllTickers-stream")
    fun getAllTickers(paginationBoundary:PaginationBoundary): Flux<TickerBoundary> {
        return this.tickerService
                   .getAllTickers(paginationBoundary.size, paginationBoundary.page)
                    .log()
    }

    // java -jar rsc-0.9.1.jar --debug --channel --data "{\"tickerId\":\"1a2bc3d4e5f6\"}" --route getTickersByIds-channel tcp://localhost:7000
    @MessageMapping("getTickersByIds-channel")
    fun getTickersByIds(idBoundaries: Flux<IdBoundary>) : Flux<TickerBoundary> {
        return idBoundaries
            .map { it.tickerId!! }
            .flatMap { tickerService.getTickerById(it) }
            .onErrorContinue { _, _ -> } // ignore errors for not-found tickers
            .log()
    }


    @MessageMapping("deleteAllTickers-fire-and-forget")
    fun deleteAllTickers(): Mono<Void> {
        return tickerService.deleteAllTickers()
    }

    @MessageMapping("getCompanyTickers-stream")
    fun getCompanyTickers(request: Mono<CompanyBoundary>): Flux<TickerBoundary> {
        return request.flatMapMany { company ->
            tickerService.getTickersByCompany(company)
        }
    }

    @MessageMapping("getRelatedTickers-channel")
    fun getRelatedTickers(ids: Flux<IdBoundary>): Flux<TickerBoundary> {
        return ids.flatMap { id ->
            tickerService.getRelatedTickers(id.tickerId!!)
        }
    }

    @MessageMapping("getTickersByExternalReferences-channel")
    fun getTickersByExternalReferences(externalReferences: Flux<ExternalReferenceBoundary>) : Flux<TickerBoundary> {
        return tickerService.getTickersByExternalReferences(externalReferences)
    }
}