package com.example.rsockettickermanagementserviceconsumer

class TickerBindBoundary {
    var tickerId: String? = null
    var relatedTickerIds:  MutableList<String>? = null


    override fun toString(): String {
        return "TickerBindBoundary(tickerId=$tickerId, relatedTickerIds=$relatedTickerIds)"
    }

}