package com.example.reactivetasksmanagementr2dbcservice

//import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface TaskCrud : ReactiveCrudRepository<TaskEntity, String> {

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

//    fun findByTaskIdNotNull(
//        pageable: Pageable
//    ): Flux<TaskEntity>
}
