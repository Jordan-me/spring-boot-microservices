package com.example.reactivetasksmanagementr2dbcservice

import org.springframework.data.annotation.Id
//import lombok.Data;
import org.springframework.data.relational.core.mapping.Table
import java.util.*

//@Document(collection = "TASKS")
//@Table("TASKS")
//@Data
@Table("TASKS")
class TaskEntity() {
    @Id
    var taskId: String? = null
    var subject: String? = null
    var categories: String? = null
    var createdTimestamp: Date? = null
    var creator: String? = null
    var associatedUsers: String? = null
    var status: String? = null
    var details: String? = null
}
