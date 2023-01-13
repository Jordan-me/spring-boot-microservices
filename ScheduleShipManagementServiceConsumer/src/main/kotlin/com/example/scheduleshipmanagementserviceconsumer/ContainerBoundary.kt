package com.example.scheduleshipmanagementserviceconsumer

class ContainerBoundary {
    var containerId:  String? = null
    var containerSize:  Int? = null
    var loadType:  String? = null

    override fun toString(): String {
        return "ContainerBoundary(containerId=$containerId, containerSize=$containerSize, loadType=$loadType)"
    }


}
