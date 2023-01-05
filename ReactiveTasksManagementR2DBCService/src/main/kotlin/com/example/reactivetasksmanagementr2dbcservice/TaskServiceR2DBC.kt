package com.example.reactivetasksmanagementr2dbcservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class TaskServiceR2DBC(
    @Autowired val crud: TaskCrud,
    @Autowired val converter: TaskConverter
): TaskService {

    @Transactional
    override fun create(task: TaskBoundary): Mono<TaskBoundary> {
        task.taskId = null
        task.createdTimestamp = Date()

        return Mono.just(task)
            .log()
            .map { this.converter.toEntity(it) }
            .flatMap { this.crud.save(it) }
            .map { this.converter.toBoundary(it) }
            .log()
    }

    @Transactional(readOnly = true)
    override fun getSpecificTask(taskId: String): Mono<TaskBoundary> {
        return this.crud
            .findById(taskId)
            .map { this.converter.toBoundary(it) }
            .log()
    }

    @Transactional
    override fun cleanup(): Mono<Void> {
        return this.crud
            .deleteAll()
            .log()
    }

    @Transactional(readOnly = true)
    override fun search(
        filterType: String,
        filterValue: String,
        sortAttribute: String,
        sortOrder: String,
        size: Int,
        page: Int
    ): Flux<TaskBoundary> {
        when (filterType) {
            "bySubject" -> {
                return this.crud
                    .findAllBySubject(filterValue, PageRequest.of(page, size,
                        getSortOrder(sortOrder) as Sort.Direction,getSortAttribute(sortAttribute) , "taskId"))
                    .map {
                        this.converter.toBoundary(it)
                    }
                    .log()
            }
            "byCreatorEmail" -> {
                return this.crud
                    .findAllByCreator(filterValue, PageRequest.of(page, size,
                        getSortOrder(sortOrder) as Sort.Direction,getSortAttribute(sortAttribute) , "taskId"))
                        .map {
                            this.converter.toBoundary(it)
                        }
                        .log()
            }
            "byStatus" -> {
                return this.crud
                    .findAllByStatus(filterValue, PageRequest.of(page, size,
                        getSortOrder(sortOrder) as Sort.Direction,getSortAttribute(sortAttribute) , "taskId"))
                    .map {
                        this.converter.toBoundary(it)
                    }
                    .log()
            }
            "createdInRange" -> {
                TODO("there is a query need implementation here")
//                return this.crud
//                    .findAllByCreatedTimestampBetween(fromDate,toDate, PageRequest.of(page, size,
//                        getSortOrder(sortOrder) as Sort.Direction,getSortAttribute(sortAttribute) , "taskId"))
//                    .stream()
//                    .map {
//                        this.converter.toBoundary(it)
//                    }
//                    .collect(Collectors.toList())
            }
            "byAssociatedUser" -> {
                return this.crud
                    .findAllByAssociatedUsersContains(filterValue, PageRequest.of(page, size,
                        getSortOrder(sortOrder) as Sort.Direction,getSortAttribute(sortAttribute) , "taskId"))
                    .map {
                        this.converter.toBoundary(it)
                    }
                    .log()
            }
        }
        if(filterType != "")
            throw InputException("$filterType is not valid option")
        return this.crud
            .findByTaskIdNotNull(PageRequest.of(page, size, getSortOrder(sortOrder) as Sort.Direction,getSortAttribute(sortAttribute) , "email"))
            .map {
                this.converter.toBoundary(it)
            }
            .log()
    }
    private fun getSortOrder(sortOrder: String): Any {
        if (sortOrder != "DESC" && sortOrder!= "ASC")
            throw InputException("$sortOrder is not valid - either ASC or DESC")
        if(sortOrder == "DESC"){
            return  Sort.Direction.DESC
        }
        return Sort.Direction.ASC
    }
    private fun getSortAttribute(sortAttribute: String): String? {
        if (sortAttribute != "taskId" &&
            sortAttribute != "subject" &&
            sortAttribute != "categories" &&
            sortAttribute != "createdTimestamp" &&
            sortAttribute != "creator" &&
            sortAttribute != "associatedUsers" &&
            sortAttribute != "status" &&
            sortAttribute != "details")
            throw InputException("$sortAttribute is not valid")
        return sortAttribute
    }


}