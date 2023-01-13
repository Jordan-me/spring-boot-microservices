package com.example.rsockettickermanagementserviceconsumer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import javax.annotation.PostConstruct

@Service
class RemoteRSocketTickerService(
    @Autowired val rsocketRequesterBuilder: RSocketRequester.Builder,
    @Value("\${remote.message.rsocket.producer.hostname}") val remoteHost:String,
    @Value("\${remote.message.rsocket.producer.port}") val remotePort:Int
): RemoteTickerService {
    lateinit var rsocketRequester:RSocketRequester

    @PostConstruct
    fun init(){
        this.rsocketRequester = this.rsocketRequesterBuilder
            .tcp(this.remoteHost, this.remotePort)
    }

    override fun createTicker(ticker: TickerBoundary): Mono<TickerBoundary> {
        return this.rsocketRequester
            .route("publish-ticker-req-resp")
            .data(ticker)
            .retrieveMono(TickerBoundary::class.java)
            .log()
    }

    override fun bind(tickerBinding: TickerBindBoundary): Mono<Void> {
        return this.rsocketRequester
            .route("bind-tickers-fire-and-forget")
            .send()
            .log()
    }

    override fun cleanup(): Mono<Void> {
        return this.rsocketRequester
            .route("deleteAllTickers-fire-and-forget")
            .send()
            .log()
    }
//
//    override fun bindTickers(tickerId: String?, relatedTickerIds: List<String>?) {
//        // Find the ticker with the given tickerId
//        val ticker = tickerId?.let { crud.findById(it) }?.block()
//        val relatedTickerIdsList = crud.findAll()
//            .filter { tickerEntity -> relatedTickerIds!!.contains(tickerEntity.tickerId!!) }
//            .map { it.tickerId }
//            .collectList()
//            .block()
//        val nonNullList: List<String> = relatedTickerIdsList.let { it.orEmpty().filterNotNull().map { it } }
//        // Update the ticker to have the related tickers
//        ticker!!.relatedTickerIds?.addAll(nonNullList)
//        crud.save(ticker)
//    }
//
//    override fun getAllTickers(): Flux<TickerEntity> {
//        return crud.findAll().switchIfEmpty(Mono.empty())
//    }
//
//    override fun getTickerById(id: String) : Mono<TickerBoundary> {
//        return Mono.just(converter.toBoundary(crud.findById(id).block()!!))
//    }
//
//    override fun deleteAllTickers(): Mono<Void> {
//        return crud.deleteAll()
//    }
//
//    override fun getTickersByCompany(company: CompanyBoundary): Flux<TickerBoundary> {
//        return crud.findByPublisherCompany(company.company!!)
//            .map { tickerEntity -> converter.toBoundary(tickerEntity) }
//    }
//
//    override fun getRelatedTickers(tickerId: String): Flux<TickerBoundary> {
//        return crud.findById(tickerId)
//            .flatMapMany { ticker ->
//                crud.findAllById(ticker.relatedTickerIds!!)
//                    .map { converter.toBoundary(it) }
//            }
//    }
//
//    override fun getTickersByExternalReferences(externalReferences: Flux<ExternalReferenceBoundary>): Flux<TickerBoundary> {
//        return externalReferences
//            .flatMap { externalReference ->
//                crud
//                    .findByExternalReferencesSystemAndExternalReferencesExternalSystemId(externalReference.system!!,externalReference.externalSystemId!!)
//                    .map { converter.toBoundary(it) }
//            }
//    }


}

