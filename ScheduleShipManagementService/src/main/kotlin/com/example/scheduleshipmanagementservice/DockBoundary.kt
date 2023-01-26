package com.example.scheduleshipmanagementservice

class DockBoundary() {
    var dockId: String? = null
    var dockType: String? = null
    var takenBy: String? = null
    var size: Size? = null

    override fun toString(): String {
        return "DockerBoundary(dockerId=$dockId, dockerType=$dockType, takenBy=$takenBy, size=$size)"
    }


}



