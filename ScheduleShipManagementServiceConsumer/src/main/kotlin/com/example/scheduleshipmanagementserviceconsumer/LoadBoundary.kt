package com.example.scheduleshipmanagementserviceconsumer

class LoadBoundary {
    var numberOfContainers: Int? = null
    var containers:  MutableList<ContainerBoundary>? = null

    override fun toString(): String {
        return "LoadBoundary(numberOfContainers=$numberOfContainers, containers=$containers)"
    }

}
