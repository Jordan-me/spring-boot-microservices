package com.example.reactivetasksmanagementservice

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface TaskService {
    fun create(task: TaskBoundary): Mono<TaskBoundary>
    fun getSpecificTask(taskId: String): Mono<TaskBoundary>
    fun cleanup(): Mono<Void>
    fun search(
        filterType: String,
        filterValue: String,
        sortAttribute: String,
        sortOrder: String,
        size: Int,
        page: Int
    ): Flux<TaskBoundary>

}
