package com.example.reactivetasksmanagementservice

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "TASKS")
class TaskEntity() {
    @Id
    var taskId: String? = null
    var subject: String? = null
    var categories: List<String>? = null
    var createdTimestamp: Date? = null
    var creator: String? = null
    var associatedUsers: List<String>? = null
    var status: String? = null
    var details: Map<String, Any>? = null
}
