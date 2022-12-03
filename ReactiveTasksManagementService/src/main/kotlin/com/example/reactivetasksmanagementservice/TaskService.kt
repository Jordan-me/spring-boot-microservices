package com.example.reactivetasksmanagementservice

import java.util.*

interface TaskService {
    fun create(task: TaskBoundary): TaskBoundary
    fun getSpecificTask(taskId: String): Optional<TaskBoundary>
    fun deleteAll()
    fun search(
        filterType: String,
        filterValue: String,
        sortAttribute: String,
        sortOrder: String,
        size: Int,
        page: Int
    ): List<TaskBoundary>

}
