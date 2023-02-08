package com.example.scheduleshipmanagementservice

import org.springframework.stereotype.Component

@Component
class ScheduleShipsConverter {
    fun toEntity (boundary: VisitBoundary): VisitEntity {
        var entity = VisitEntity()

        if (boundary.visitId != null) {
            entity.visitId = boundary.visitId!!
        }
        entity.shipId = boundary.shipId
        entity.shipName = boundary.shipName
        entity.dock = boundary.dock
        entity.timeIn = boundary.timeIn
        entity.timeOut = boundary.timeOut
        entity.shipType = boundary.shipType
        entity.shipStatus = boundary.shipStatus

        entity.shipSize = boundary.shipSize
        entity.weightTons = boundary.weightTons
        entity.load = boundary.load

        return entity
    }
    fun convertIdToEntity (id:String):String{
        return id
    }
    fun toBoundary(entity: VisitEntity) : VisitBoundary {
        var boundary = VisitBoundary()
        boundary.visitId = entity.visitId
        boundary.shipId = entity.shipId
        boundary.shipName = entity.shipName
        boundary.dock = entity.dock
//        entity.indexQueue = boundary.indexQueue
        boundary.timeIn = entity.timeIn
        boundary.timeOut = entity.timeOut
        boundary.shipType = entity.shipType
        boundary.shipStatus = entity.shipStatus

        boundary.shipSize = entity.shipSize
        boundary.weightTons = entity.weightTons
        boundary.load = entity.load

        return boundary

    }

    fun toEntity (boundary: DockBoundary): DockEntity {
        var entity = DockEntity()

        if (boundary.dockId != null) {
            entity.dockId = boundary.dockId!!
        }
        entity.type = boundary.type
        entity.takenBy = boundary.takenBy
        entity.size = boundary.size

        return entity
    }

    fun toBoundary(entity: DockEntity) : DockBoundary {
        var boundary = DockBoundary()

        boundary.dockId = entity.dockId
        boundary.type = entity.type
        boundary.takenBy = entity.takenBy
        boundary.size = entity.size
        return boundary

    }
}