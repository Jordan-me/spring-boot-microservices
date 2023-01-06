package com.example.rsockettickermanagementservice

class TickerBindBoundary {
    var tickerId: String? = null
    var relatedTickerIds: List<String>? = null


    override fun toString(): String {
        return "TickerBindBoundary(tickerId=$tickerId, relatedTickerIds=$relatedTickerIds)"
    }

}