package com.example.reactivetasksmanagementservice

import org.springframework.stereotype.Component
import java.util.*

@Component
class TaskConverter {
    fun toBoundary(entity:TaskEntity) :TaskBoundary {
        var boundary = TaskBoundary()

        boundary.taskId = entity.taskId
        boundary.subject = entity.subject
        boundary.categories = entity.categories
        boundary.createdTimestamp = entity.createdTimestamp
        boundary.creator = entity.creator
        boundary.associatedUsers = entity.associatedUsers
        boundary.status = entity.status
        boundary.details = entity.details
        return boundary
    }
    fun toEntity (boundary:TaskBoundary):TaskEntity {
        var entity = TaskEntity()

        if (boundary.taskId != null) {
            entity.taskId = boundary.taskId!!
        }
        entity.subject = boundary.subject
        entity.categories = boundary.categories
        entity.createdTimestamp = boundary.createdTimestamp
        entity.creator = boundary.creator
        entity.associatedUsers = boundary.associatedUsers
        entity.status = boundary.status
        entity.details = boundary.details

        return entity
    }


}
