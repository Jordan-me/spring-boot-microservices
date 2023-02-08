package com.example.scheduleshipmanagementservice

class DockBoundary() {
    var id: String? = null
    var type: String? = null
    var takenBy: String? = null
    var size: Size? = null

    override fun toString(): String {
        return "DockBoundary(id=$id, type=$type, takenBy=$takenBy, size=$size)"
    }


}



