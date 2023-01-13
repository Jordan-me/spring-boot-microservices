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
    @RequestMapping(
        path = ["/ticker/byCompany/{company}"],
        method = [RequestMethod.GET],
        produces = [MediaType.TEXT_EVENT_STREAM_VALUE]
    )
    fun getTickersByCompany(
        @PathVariable("company") company:String,
        @RequestParam(name="size", required = false, defaultValue = "10") size:Int,
        @RequestParam(name="page", required = false, defaultValue = "0") page:Int
    ):Flux<TickerBoundary>{
        return this.rsocketTickers
            .getTickersByCompany(company, size, page)
    }
    @RequestMapping(
        path = ["/ticker/relatedTickers/{ids}"],
        method = [RequestMethod.GET],
        produces = [MediaType.TEXT_EVENT_STREAM_VALUE]
    )
    fun getRelatedTickers(
        @PathVariable("ids") ids:String,
    ):Flux<TickerBoundary>{
        val list = IdsBoundary(
            ids.split(",").toTypedArray().toMutableList())
        return this.rsocketTickers
            .getRelatedTickers(list)
    }
    @RequestMapping(
        path = ["/ticker/byExternalReferences/{systems}/{externalSystemIds}"],
        method = [RequestMethod.GET],
        produces = [MediaType.TEXT_EVENT_STREAM_VALUE]
    )
    fun getTickersByExternalReferences(
        @PathVariable("systems") systems:String,
        @PathVariable("externalSystemIds") externalSystemIds:String,
    ):Flux<TickerBoundary>{
        val listIds = externalSystemIds.split(",").toTypedArray().toMutableList()
        val listExternal = Flux.fromIterable(systems.split(",").toTypedArray().toMutableList())
            .zipWith(
                Flux.fromIterable(listIds)
            ).map {
                ExternalReferenceBoundary(it.t1,it.t2)
            }
        return this.rsocketTickers
            .getTickersByExternalReferences(listExternal)
    }

    @RequestMapping(
        path = ["/tickers"],
        method = [RequestMethod.GET],
        produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getAll(@RequestParam(name="size", required = false, defaultValue = "10") size:Int,
               @RequestParam(name="page", required = false, defaultValue = "0") page:Int):Flux<TickerBoundary>{
        return this.rsocketTickers
            .getAllTickers(size, page)
    }

    @RequestMapping(
        path = ["/tickers/byIds/{ids}"],
        method = [RequestMethod.GET],
        produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getByIds (@PathVariable("ids") ids:String):Flux<TickerBoundary>{
        val list = IdsBoundary(
            ids.split(",").toTypedArray().toMutableList())

        return this.rsocketTickers
            .getByIds(list)
    }

}