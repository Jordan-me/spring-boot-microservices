package com.example.rsockettickermanagementservice

import org.springframework.stereotype.Component
import java.util.*

@Component
class TickerConverter {
    fun toEntity (boundary: TickerBoundary): TickerEntity {
        var entity = TickerEntity()

        if (boundary.tickerId != null) {
            entity.tickerId = boundary.tickerId!!
        }
        entity.publisher = boundary.publisher
        entity.publishedTimestamp = boundary.publishedTimestamp
        entity.tickerType = boundary.tickerType
        entity.summary = boundary.summary
        entity.externalReferences = boundary.externalReferences
        entity.relatedTickerIds = boundary.relatedTickerIds
        entity.tickerDetails = boundary.tickerDetails

        return entity
    }

    fun toBoundary(entity: TickerEntity) : TickerBoundary {
        var boundary = TickerBoundary()

        boundary.tickerId = entity.tickerId.toString()
        boundary.publisher = entity.publisher
        boundary.publishedTimestamp = entity.publishedTimestamp
        boundary.tickerType = entity.tickerType
        boundary.summary = entity.summary
        boundary.externalReferences = entity.externalReferences
        boundary.relatedTickerIds = entity.relatedTickerIds
        boundary.tickerDetails = entity.tickerDetails

        return boundary

    }

    fun convertIdToEntity(id: String): String {
        return id
    }


}
