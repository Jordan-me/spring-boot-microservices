package com.example.rsockettickermanagementserviceconsumer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
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

    override fun getAllTickers(size: Int, page: Int): Flux<TickerBoundary> {
        val paginationData = PaginationBoundary(size, page)
        return this.rsocketRequester
            .route("getAllTickers-stream")
            .data(paginationData)
            .retrieveFlux(TickerBoundary::class.java)
            .log()
    }

    override fun getByIds(ids: IdsBoundary): Flux<TickerBoundary> {
        val idsFlux = Flux.fromIterable(ids.tickerIds!!)
            .map { IdBoundary(it) }

        return this.rsocketRequester
            .route("getTickersByIds-channel")
            .data(idsFlux)
            .retrieveFlux(TickerBoundary::class.java)
            .log()
    }

}

