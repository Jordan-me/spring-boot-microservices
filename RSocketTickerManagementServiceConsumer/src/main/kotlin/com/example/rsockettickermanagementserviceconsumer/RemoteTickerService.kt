package com.example.rsockettickermanagementserviceconsumer

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface RemoteTickerService {

    fun createTicker(ticker: TickerBoundary): Mono<TickerBoundary>
    fun bind(tickerBinding: TickerBindBoundary): Mono<Void>
    fun cleanup(): Mono<Void>
//    fun bindTickers(tickerId: String?, relatedTickerIds: List<String>?)
//    fun getAllTickers(): Flux<TickerBoundary>
//    fun getTickerById(id: String) : Mono<TickerBoundary>
//    fun deleteAllTickers(): Mono<Void>
//    fun getTickersByCompany(company: CompanyBoundary):Flux<TickerBoundary>
//    fun getRelatedTickers(tickerId: String): Flux<TickerBoundary>
//    fun getTickersByExternalReferences(externalReferences: Flux<ExternalReferenceBoundary>): Flux<TickerBoundary>

}
