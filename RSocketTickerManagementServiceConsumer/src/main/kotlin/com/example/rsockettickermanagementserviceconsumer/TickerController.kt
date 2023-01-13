package com.example.rsockettickermanagementserviceconsumer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
class TickerController(
    @Autowired val rsocketTickers: RemoteTickerService
    ){

    @RequestMapping(
        path = ["/ticker"],
        method = [RequestMethod.POST],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createTicker(@RequestBody ticker:TickerBoundary):Mono<TickerBoundary>{
        return this.rsocketTickers
            .createTicker(ticker)
    }

    @RequestMapping(path=["/ticker/bind"],
        method = [RequestMethod.PUT],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun bindTickers (@RequestBody tickerBinding:TickerBindBoundary):Mono<Void>{
        return this.rsocketTickers
            .bind(tickerBinding)

    }

    @RequestMapping(
        path = ["/ticker"],
        method = [RequestMethod.DELETE])
    fun cleanup():Mono<Void>{
        return this.rsocketTickers
            .cleanup()
    }

//    deleteAllTickers-fire-and-forget

//
////     java -jar rsc-0.9.1.jar --debug --fnf --route bind-tickers-fire-and-forget tcp://localhost:7000
//    @MessageMapping("bind-tickers-fire-and-forget")
//    fun bindTickers(request: TickerBindBoundary) {
//        tickerService.bindTickers(request.tickerId, request.relatedTickerIds)
//    }
//
////     java -jar rsc-0.9.1.jar --debug --stream --data "{\"tickerId\":\"1a2bc3d4e5f6\",\"relatedTickerIds\":[\"1a2bc3d4e592", \"1a2bc3d42aab\"]}" --route getAllTickers-stream tcp://localhost:7000
//    @MessageMapping("getAllTickers-stream")
//    fun getAllTickers(): Flux<TickerEntity> {
//        return tickerService.getAllTickers()
//    }
//
//    // java -jar rsc-0.9.1.jar --debug --channel --data "{\"tickerId\":\"1a2bc3d4e5f6\"}" --route getTickersByIds-channel tcp://localhost:7000
//    @MessageMapping("getTickersByIds-channel")
//    fun getTickersByIds(idBoundaries: Flux<IdBoundary>) : Flux<TickerBoundary> {
//        return idBoundaries
//            .map { it.tickerId }
//            .flatMap { tickerService.getTickerById(it!!) }
//            .onErrorContinue { _, _ -> } // ignore errors for not-found tickers
//    }
//
//
//    @MessageMapping("deleteAllTickers-fire-and-forget")
//    fun deleteAllTickers(): Mono<Void> {
//        return tickerService.deleteAllTickers()
//    }
//
//    @MessageMapping("getCompanyTickers-stream")
//    fun getCompanyTickers(request: Mono<CompanyBoundary>): Flux<TickerBoundary> {
//        return request.flatMapMany { company ->
//            tickerService.getTickersByCompany(company)
//        }
//    }
//
//    @MessageMapping("getRelatedTickers-channel")
//    fun getRelatedTickers(ids: Flux<IdBoundary>): Flux<TickerBoundary> {
//        return ids.flatMap { id ->
//            tickerService.getRelatedTickers(id.tickerId!!)
//        }
//    }
//
//    @MessageMapping("getTickersByExternalReferences-channel")
//    fun getTickersByExternalReferences(externalReferences: Flux<ExternalReferenceBoundary>) : Flux<TickerBoundary> {
//        return tickerService.getTickersByExternalReferences(externalReferences)
//    }
}