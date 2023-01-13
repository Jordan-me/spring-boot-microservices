package com.example.scheduleshipmanagementservice

import org.springframework.stereotype.Component
import java.util.*

@Component
class ScheduleShipsConverter {
    fun toEntity (boundary: VisitBoundary): VisitEntity {
        var entity = VisitEntity()

        if (boundary.visitId != null) {
            entity.visitId = boundary.visitId!!
        }
        entity.shipId = boundary.shipId
        entity.shipName = boundary.shipName
        entity.docker = boundary.docker
        entity.timeIn = boundary.timeIn
        entity.timeOut = boundary.timeOut
        entity.shipType = boundary.shipType
        entity.shipStatus = boundary.shipStatus

        entity.shipSize = boundary.shipSize
        entity.weightTons = boundary.weightTons
        entity.load = boundary.load

        return entity
    }

    fun toBoundary(entity: VisitEntity) : VisitBoundary {
        var boundary = VisitBoundary()
        boundary.visitId = entity.visitId
        boundary.shipId = entity.shipId
        boundary.shipName = entity.shipName
        boundary.docker = entity.docker
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

    fun toEntity (boundary: DockerBoundary): DockerEntity {
        var entity = DockerEntity()

        if (boundary.dockerId != null) {
            entity.dockerId = boundary.dockerId!!
        }
        entity.dockerType = boundary.dockerType
        entity.takenBy = boundary.takenBy
        entity.size = boundary.size

        return entity
    }

    fun toBoundary(entity: DockerEntity) : DockerBoundary {
        var boundary = DockerBoundary()

        boundary.dockerId = entity.dockerId
        boundary.dockerType = entity.dockerType
        boundary.takenBy = entity.takenBy
        boundary.size = entity.size
        return boundary

    }
}