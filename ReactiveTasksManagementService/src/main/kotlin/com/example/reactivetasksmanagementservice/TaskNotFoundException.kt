package com.example.reactivetasksmanagementservice

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND)
class TaskNotFoundException: RuntimeException {
    constructor():super()

    constructor(info:String):super(info)

    constructor(info:String, cause:Throwable): super(info, cause)
}
