package com.example.rsockettickermanagementserviceconsumer

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface RemoteTickerService {

    fun createTicker(ticker: TickerBoundary): Mono<TickerBoundary>
    fun bind(tickerBinding: TickerBindBoundary): Mono<Void>
    fun cleanup(): Mono<Void>
    fun getTickersByCompany(company: String, size: Int, page: Int): Flux<TickerBoundary>
    fun getRelatedTickers(list: IdsBoundary): Flux<TickerBoundary>
    fun getTickersByExternalReferences(listExternal: Flux<ExternalReferenceBoundary>): Flux<TickerBoundary>
//    fun getAllTickers(): Flux<TickerBoundary>
//    fun getTickerById(id: String) : Mono<TickerBoundary>

}
