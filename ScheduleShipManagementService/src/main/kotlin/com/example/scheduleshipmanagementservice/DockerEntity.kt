package com.example.scheduleshipmanagementservice

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "DOCKERS")
class DockerEntity() {
    @Id
    var dockerId: String? = null
    var dockerType: String? = null
    var takenBy: String? = null
    var size: Size? = null
}