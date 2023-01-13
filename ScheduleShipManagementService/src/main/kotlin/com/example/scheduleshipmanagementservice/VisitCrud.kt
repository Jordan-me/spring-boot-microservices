package com.example.scheduleshipmanagementservice

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface VisitCrud : ReactiveMongoRepository<VisitEntity, String>
{

}