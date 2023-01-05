package com.example.reactivetasksmanagementservice

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.repository.query.Param
import reactor.core.publisher.Flux

interface TaskCrud : ReactiveMongoRepository<TaskEntity, String> {

    fun findAllBySubject(
        @Param("subject") subject: String,
        pageable: Pageable
    ):Flux<TaskEntity>

    fun findAllByCreator(
        @Param("creator") creator: String,
        pageable: Pageable
    ):Flux<TaskEntity>

    fun findAllByStatus(
        @Param("status") status: String,
        pageable: Pageable
    ):Flux<TaskEntity>

    fun findAllByCreatedTimestampBetween(
        @Param("fromDate") fromDate: String,
        @Param("toDate") toDate: String,
        pageable: Pageable
    ):Flux<TaskEntity>

    fun findAllByAssociatedUsersContains(
        @Param("email") email: String,
        pageable: Pageable
    ):Flux<TaskEntity>

    fun findByTaskIdNotNull(
        pageable: Pageable
    ): Flux<TaskEntity>
}
