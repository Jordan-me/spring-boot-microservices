package com.example.scheduleshipmanagementservice

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "VISITS")
class VisitEntity {
    @Id
    var visitId: String? = null
    var shipId: String? = null
    var shipName: String? = null
    var docker: String? = null
    var timeIn: Date? = null
    var timeOut: Date? = null
    var shipType: String? = null
    var shipStatus: String? = null

    var shipSize: Size? = null
    var weightTons: Float? = null
    var load: LoadBoundary? = null

}
