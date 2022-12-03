package com.example.reactivetasksmanagementservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
class TasksController(
    @Autowired val taskService: TaskService
    ){

    @RequestMapping(
        path = ["/tasks"],
        method = [RequestMethod.POST],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun create(@RequestBody task:TaskBoundary):TaskBoundary{
        return this.taskService.create(task)
    }
    @RequestMapping(
        path = ["/tasks/{taskId}"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getTaskById(@PathVariable taskId: String): TaskBoundary {
        return this.taskService
            .getSpecificTask(taskId)
            .orElseThrow { TaskNotFoundException("could not find task by id $taskId") }
    }

    @RequestMapping(
        path = ["/tasks"],
        method = [RequestMethod.DELETE]
    )
    fun deleteAllTasks() {
        this.taskService
            .deleteAll()
    }

    @RequestMapping(
        path = ["/tasks"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun searchUserByAttribute(
        @RequestParam(name = "filterType", required = false, defaultValue = "") filterType:String,
        @RequestParam(name = "filterValue", required = false, defaultValue = "") filterValue:String,
        @RequestParam(name = "sortBy", required = false, defaultValue = "taskId") sortAttribute:String,
        @RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") sortOrder:String,
        @RequestParam(name = "page", required = false, defaultValue = "0") page:Int,
        @RequestParam(name = "size", required = false, defaultValue = "10") size:Int
    ): List<TaskBoundary> {
        return this.taskService
            .search(
                filterType,
                filterValue,
                sortAttribute,
                sortOrder,
                size,
                page
            )

    }
}