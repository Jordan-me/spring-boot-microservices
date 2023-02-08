package com.example.scheduleshipmanagementservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.core.publisher.Hooks

@SpringBootApplication
class ScheduleShipManagementServiceApplication

fun main(args: Array<String>) {
	Hooks.onOperatorDebug()
	runApplication<ScheduleShipManagementServiceApplication>(*args)
}
