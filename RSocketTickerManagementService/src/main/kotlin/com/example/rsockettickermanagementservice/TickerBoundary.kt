package com.example.rsockettickermanagementservice

import java.util.*

class TickerBoundary {
    var tickerId: String? = null
    var publisher: Publisher? = null
    var publishedTimestamp: Date? = null
    var tickerType: String? = null
    var summary: String? = null
    var externalReferences: MutableList<ExternalReferenceBoundary>? = null
    var relatedTickerIds: MutableList<String>? = null
    var tickerDetails: Map<String, Any>? = null

    override fun toString(): String {
        return "TickerBoundary(tickerId=$tickerId, publisher=$publisher, publishedTimestamp=$publishedTimestamp, tickerType=$tickerType, summary=$summary, extrenalReferences=$externalReferences, relatedTickerIds=$relatedTickerIds, tickerDetails=$tickerDetails)"
    }


}
