package com.example.rsockettickermanagementservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RSocketTickerManagementServiceApplication

fun main(args: Array<String>) {
	runApplication<RSocketTickerManagementServiceApplication>(*args)
}
