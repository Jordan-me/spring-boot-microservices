package com.example.scheduleshipmanagementservice

import com.example.scheduleshipmanagementserviceconsumer.LoadBoundary
import java.util.Date

class VisitBoundary() {
    var visitId: String? = null
    var shipId: String? = null
    var shipName: String? = null
    var docker: String? = null
    var indexQueue: Int? = null
    var timeIn: Date? = null
    var timeOut: Date? = null
    var shipType: String? = null
    var shipStatus: String? = null

    var shipSize: Size? = null
    var weightTons: Float? = null
    var load: LoadBoundary? = null

    override fun toString(): String {
        return "VisitBoundary(visitId=$visitId, shipId=$shipId, shipName=$shipName, docker=$docker, indexQueue=$indexQueue, timeIn=$timeIn, timeOut=$timeOut, shipType=$shipType, shipStatus=$shipStatus, shipSize=$shipSize, weightTons=$weightTons, load=$load)"
    }


}
