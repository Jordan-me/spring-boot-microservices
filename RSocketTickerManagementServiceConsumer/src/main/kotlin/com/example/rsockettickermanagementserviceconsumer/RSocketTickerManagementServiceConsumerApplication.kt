package com.example.rsockettickermanagementserviceconsumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RSocketTickerManagementServiceConsumerApplication

fun main(args: Array<String>) {
	runApplication<RSocketTickerManagementServiceConsumerApplication>(*args)
}
