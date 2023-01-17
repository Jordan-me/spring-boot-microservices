package com.example.scheduleshipmanagementserviceconsumer

import com.example.scheduleshipmanagementservice.Size

class DockerBoundary() {
    var dockerId: String? = null
    var dockerType: String? = null
    var takenBy: String? = null
    var size: Size? = null

    override fun toString(): String {
        return "DockerBoundary(dockerId=$dockerId, dockerType=$dockerType, takenBy=$takenBy, size=$size)"
    }


}


