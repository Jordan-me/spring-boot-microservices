package com.example.reactivetasksmanagementr2dbcservice

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface TaskService {
    fun create(task: TaskBoundary): Mono<TaskBoundary>
    fun getSpecificTask(taskId: String): Mono<TaskBoundary>
    fun cleanup(): Mono<Void>
    fun search(
        filterType: String,
        filterValue: String,
        fromDate: String,
        toDate: String,
        sortAttribute: String,
        sortOrder: String,
        size: Int,
        page: Int
    ): Flux<TaskBoundary>

}
