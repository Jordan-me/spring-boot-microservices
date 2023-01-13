package com.example.scheduleshipmanagementservice

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface DockerCrud : ReactiveMongoRepository<DockerEntity, String>{

}