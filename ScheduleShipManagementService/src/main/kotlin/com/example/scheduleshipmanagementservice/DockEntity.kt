package com.example.scheduleshipmanagementservice

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "DOCKS")
class DockEntity() {
    @Id
    var id: String? = null
    var type: String? = null
    var takenBy: String? = null
    var size: Size? = null
}