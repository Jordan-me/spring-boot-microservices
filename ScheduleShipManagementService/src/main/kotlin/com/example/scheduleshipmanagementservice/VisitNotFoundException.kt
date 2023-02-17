package com.example.scheduleshipmanagementservice

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND)
class VisitNotFoundException(message: String) : RuntimeException(message) {

}
