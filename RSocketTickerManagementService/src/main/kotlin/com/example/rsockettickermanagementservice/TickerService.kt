package com.example.rsockettickermanagementservice

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface TickerService {
    fun create(ticker: TickerBoundary): Mono<TickerBoundary>
    fun bindTickers(tickerId: String?, relatedTickerIds: List<String>?)
    fun getAllTickers(): Flux<TickerEntity>
    fun getTickerById(id: String) : Mono<TickerBoundary>
    fun deleteAllTickers(): Mono<Void>
    fun getTickersByCompany(company: CompanyBoundary):Flux<TickerBoundary>
    fun getRelatedTickers(tickerId: String): Flux<TickerBoundary>
    fun getTickersByExternalReferences(externalReferences: Flux<ExternalReferenceBoundary>): Flux<TickerBoundary>
}
