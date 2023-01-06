package com.example.reactivetasksmanagementr2dbcservice

import org.springframework.stereotype.Component
@Component
class TaskConverter {
    fun toBoundary(entity: TaskEntity) : TaskBoundary {
        var boundary = TaskBoundary()

        boundary.taskId = entity.taskId
        boundary.subject = entity.subject
        val separator = ", "
        boundary.categories = entity.categories?.split(separator)?.toList()
        boundary.createdTimestamp = entity.createdTimestamp
        boundary.creator = entity.creator
        boundary.associatedUsers = entity.associatedUsers?.split(separator)?.toList()
        boundary.status = entity.status
        boundary.details = entity.details?.split(",")?.associate {
            val (left, right) = it.split("=")
            left to right
        }
        return boundary
    }
    fun toEntity (boundary: TaskBoundary): TaskEntity {
        var entity = TaskEntity()
        if (boundary.taskId != null) {
            entity.taskId = boundary.taskId!!
        }
//        else{
//            entity.taskId = UUID.randomUUID().toString()
//        }
        entity.subject = boundary.subject
        val separator = ", "
        entity.categories = boundary.categories?.joinToString(separator)
        entity.createdTimestamp = boundary.createdTimestamp
        entity.creator = boundary.creator
        entity.associatedUsers = boundary.associatedUsers?.joinToString(separator)
        entity.status = boundary.status
        entity.details = boundary.details?.entries?.joinToString(separator) { "${it.key}=${it.value}" }

        return entity
    }


}
