package com.example.rsockettickermanagementservice

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "TICKERS")
class TickerEntity() {
    @Id var tickerId: String? = null
    var publisher: Publisher? = null
    var publishedTimestamp: Date? = null
    var tickerType: String? = null
    var summary: String? = null
    var externalReferences: MutableList<ExternalReferenceBoundary>? = null
    var relatedTickerIds: MutableList<String>? = null
    var tickerDetails: Map<String, Any>? = null
}
