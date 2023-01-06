package com.example.reactivetasksmanagementr2dbcservice

import java.time.LocalDate
import java.util.*

class TaskBoundary {
    var taskId: String? = null
    var subject: String? = null
    var categories: List<String>? = null
    var createdTimestamp: LocalDate? = null
    var creator: String? = null
    var associatedUsers: List<String>? = null
    var status: String? = null
    var details: Map<String, Any>? = null

    override fun toString(): String {
        return "TaskBoundary(id=$taskId, subject=$subject, categories=$categories, createdTimestamp=$createdTimestamp, creator=$creator, associatedUsers=$associatedUsers, status=$status, details=$details)"
    }

}
