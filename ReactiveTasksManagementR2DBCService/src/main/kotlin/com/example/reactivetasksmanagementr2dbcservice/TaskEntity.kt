package com.example.reactivetasksmanagementr2dbcservice

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate



@Table("tasks")
class TaskEntity() {
    @Id
    var taskId: String? = null
    var subject: String? = null
    var categories: String? = null
    var createdTimestamp: LocalDate? = null
    var creator: String? = null
    var associatedUsers: String? = null
    var status: String? = null
    var details: String? = null
}
/*
 CREATE TABLE tasks (task_id UUID PRIMARY KEY DEFAULT gen_random_uuid() ,subject varchar(255) ,categories varchar(255)  ,created_timestamp date,creator varchar(255),associated_users varchar(255),status varchar(255),details TEXT);
 */
