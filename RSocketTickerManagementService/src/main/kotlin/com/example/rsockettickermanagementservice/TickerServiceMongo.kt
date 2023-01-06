package com.example.rsockettickermanagementservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class TickerServiceMongo(
    @Autowired val crud: TickerCrud,
    @Autowired val converter: TickerConverter
): TickerService {

    override fun create(ticker: TickerBoundary): Mono<TickerBoundary> {
        ticker.tickerId = null
        ticker.publishedTimestamp = Date()
//TODO: pass on relatedTickerIds if this id not exist ignore and not save it

//        return Mono.just(ticker.relatedTickerIds)
//            .flatMapMany { Flux.fromIterable(it) } // convert the list of ids to a Flux
//            .flatMap { crud.findById(it) } // fetch the ticker by id
//            .map { it.tickerId } // extract the id of the ticker
//            .collectList() // collect the ids in a list
//            .flatMap { crud.save(ticker.copy(relatedTickerIds = it)) } // save the ticker with the filtered list of ids
//            .then()
//
        return Mono.just(ticker)
            .log()
            .map { this.converter.toEntity(it) }
            .flatMap { this.crud.save(it) }
            .map { this.converter.toBoundary(it) }
            .log()
    }

    override fun bindTickers(tickerId: String?, relatedTickerIds: List<String>?) {
        // Find the ticker with the given tickerId
        val ticker = tickerId?.let { crud.findById(it) }?.block()
        val relatedTickerIdsList = crud.findAll()
            .filter { tickerEntity -> relatedTickerIds!!.contains(tickerEntity.tickerId!!) }
            .map { it.tickerId }
            .collectList()
            .block()
        val nonNullList: List<String> = relatedTickerIdsList.let { it.orEmpty().filterNotNull().map { it } }
        // Update the ticker to have the related tickers
        ticker!!.relatedTickerIds?.addAll(nonNullList)
        crud.save(ticker)
    }

    override fun getAllTickers(): Flux<TickerEntity> {
        return crud.findAll().switchIfEmpty(Mono.empty())
    }

    override fun getTickerById(id: String) : Mono<TickerBoundary> {
        return Mono.just(converter.toBoundary(crud.findById(id).block()!!))
    }

    override fun deleteAllTickers(): Mono<Void> {
        return crud.deleteAll()
    }

    override fun getTickersByCompany(company: CompanyBoundary): Flux<TickerBoundary> {
        return crud.findByPublisherCompany(company.company!!)
            .map { tickerEntity -> converter.toBoundary(tickerEntity) }
    }

    override fun getRelatedTickers(tickerId: String): Flux<TickerBoundary> {
        return crud.findById(tickerId)
            .flatMapMany { ticker ->
                crud.findAllById(ticker.relatedTickerIds!!)
                    .map { converter.toBoundary(it) }
            }
    }

    override fun getTickersByExternalReferences(externalReferences: Flux<ExternalReferenceBoundary>): Flux<TickerBoundary> {
        return externalReferences
            .flatMap { externalReference ->
                crud
                    .findByExternalReferencesSystemAndExternalReferencesExternalSystemId(externalReference.system!!,externalReference.externalSystemId!!)
                    .map { converter.toBoundary(it) }
            }
    }


}

