package com.example.rsockettickermanagementservice

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface TickerService {
    fun create(ticker: TickerBoundary): Mono<TickerBoundary>
    fun bindTickers(tickerId: String, relatedTickerIds: MutableList<String>?): Mono<Void>
    fun getAllTickers(size: Int, page: Int): Flux<TickerBoundary>
    fun getTickerById(id: String) : Mono<TickerBoundary>
    fun deleteAllTickers(): Mono<Void>
    fun getTickersByCompany(company: CompanyBoundary):Flux<TickerBoundary>
    fun getRelatedTickers(tickerId: String): Flux<TickerBoundary>
    fun getTickersByExternalReferences(externalReferences: Flux<ExternalReferenceBoundary>): Flux<TickerBoundary>
}
