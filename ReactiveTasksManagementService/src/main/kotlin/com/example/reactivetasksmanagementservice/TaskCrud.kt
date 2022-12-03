package com.example.reactivetasksmanagementservice

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.Param

interface TaskCrud : MongoRepository<TaskEntity, String> {

    fun findAllBySubject(
        @Param("subject") subject: String,
        pageable: Pageable
    ):List<TaskEntity>

    fun findAllByCreator(
        @Param("creator") creator: String,
        pageable: Pageable
    ):List<TaskEntity>

    fun findAllByStatus(
        @Param("status") status: String,
        pageable: Pageable
    ):List<TaskEntity>

    fun findAllByCreatedTimestampBetween(
        @Param("fromDate") fromDate: String,
        @Param("toDate") toDate: String,
        pageable: Pageable
    ):List<TaskEntity>

    fun findAllByAssociatedUsersContains(
        @Param("email") email: String,
        pageable: Pageable
    ):List<TaskEntity>
}
