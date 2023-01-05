package com.example.reactivetasksmanagementr2dbcservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
@EnableR2dbcRepositories
class ReactiveTasksManagementServiceApplication

fun main(args: Array<String>) {
	runApplication<ReactiveTasksManagementServiceApplication>(*args)
}
