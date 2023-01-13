package com.example.scheduleshipmanagementserviceconsumer

import com.example.scheduleshipmanagementserviceconsumer.ContainerBoundary

class LoadBoundary {
    var numberOfContainers: Int? = null
    var containers:  MutableList<ContainerBoundary>? = null

    override fun toString(): String {
        return "LoadBoundary(numberOfContainers=$numberOfContainers, containers=$containers)"
    }

}
