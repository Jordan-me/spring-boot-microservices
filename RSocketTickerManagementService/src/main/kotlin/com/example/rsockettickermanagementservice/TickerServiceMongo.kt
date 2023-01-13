package com.example.rsockettickermanagementservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class TickerServiceMongo(
    @Autowired val crud: TickerCrud,
    @Autowired val converter: TickerConverter
): TickerService {

    fun getExistingIds( ids: MutableList<String>?):Flux<String>{
        val tickerIds = ids ?: emptyList()
        return Flux.fromIterable(tickerIds)
            .map { it!! }
            .flatMap { this.crud.findById(it) }
            .map { this.converter.toBoundary(it).tickerId!! }
            .log()
    }
    override fun create(ticker: TickerBoundary): Mono<TickerBoundary> {
        ticker.tickerId = null
        ticker.publishedTimestamp = Date()
        val tickerIds = ticker.relatedTickerIds ?: mutableListOf<String>()
        ticker.relatedTickerIds = mutableListOf<String>()
//        Filter only existing related Ids
        return Mono.just(ticker)
            .zipWith(
                getExistingIds(tickerIds).collectList()
            ).map{tuple->
                tuple.t1.relatedTickerIds = tuple.t2
            }
            .then(
//                Save the object on DB
                Mono.just(ticker)
                .log()
                .map {boundary->
                    this.converter.toEntity(boundary)
                }
                .flatMap { this.crud.save(it) }
                .map { this.converter.toBoundary(it) }
                .log()
            )
    }

    override fun bindTickers(tickerId: String, relatedTickerIds:  MutableList<String>?) : Mono<Void>{
        return this.crud
            .findById(tickerId)
            .zipWith(
                getExistingIds(relatedTickerIds).collectList()
            ).flatMap {tuple->
                tuple.t1.relatedTickerIds?.addAll(tuple.t2)
                this.crud
                    .save(tuple.t1)
            }
            .then()
    }

    override fun getAllTickers(size: Int, page: Int): Flux<TickerBoundary> {
        return this.crud
            .findAllByTickerIdNotNull(PageRequest.of(page, size, Sort.Direction.DESC, "summary", "tickerId"))
            .map { this.converter.toBoundary(it) }
            .log()

    }

    override fun getTickerById(id: String) : Mono<TickerBoundary> {
        return this.crud
            .findById(this.converter.convertIdToEntity(id))
            .map { this.converter.toBoundary(it) }
            .log()
    }

    override fun deleteAllTickers(): Mono<Void> {
        return crud.deleteAll()
    }

    override fun getTickersByCompany(company: CompanyBoundary): Flux<TickerBoundary> {
        return this.crud.findByPublisherCompany(company.company!!)
            .map { tickerEntity -> this.converter.toBoundary(tickerEntity) }
    }

    override fun getRelatedTickers(tickerId: String): Flux<TickerBoundary> {
        return this.crud.findById(tickerId)
            .flatMapMany { ticker ->
                this.crud.findAllById(ticker.relatedTickerIds!!)
                    .map { this.converter.toBoundary(it) }
            }
    }

    override fun getTickersByExternalReferences(externalReferences: Flux<ExternalReferenceBoundary>): Flux<TickerBoundary> {
        return externalReferences
            .flatMap { externalReference ->
                this.crud
                    .findByExternalReferencesSystemAndExternalReferencesExternalSystemId(externalReference.system!!,externalReference.externalSystemId!!)
                    .map { this.converter.toBoundary(it) }
            }
    }


}

