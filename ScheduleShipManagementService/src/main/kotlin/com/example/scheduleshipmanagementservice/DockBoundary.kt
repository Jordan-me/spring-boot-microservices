package com.example.scheduleshipmanagementservice

class DockBoundary() {
    var dockId: String? = null
    var type: String? = null
    var takenBy: String? = null
    var size: Size? = null

    override fun toString(): String {
        return "DockBoundary(dockId=$dockId, type=$type, takenBy=$takenBy, size=$size)"
    }


}



