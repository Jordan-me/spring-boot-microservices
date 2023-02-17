package com.example.scheduleshipmanagementservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class ScheduleServiceMongo (
    @Autowired    val dockCrud: DockCrud,
    @Autowired    val visitCrud: VisitCrud,
    @Autowired    val converter: ScheduleShipsConverter
): ScheduleService {

    override fun deleteVisits(): Mono<Void> {
        return this.visitCrud.deleteAll()
    }

    override fun deleteDocks(): Mono<Void> {
        return this.dockCrud.deleteAll()
    }

    override fun getDocks(pageable: PageRequest): Flux<DockBoundary> {
        return this.dockCrud.findAll(pageable.sort)
            .map {Mono.just(this.converter.toBoundary(it)) }
            .flatMap { it }
            .log()
    }

    /**
     * count all the ships on queue and retrive the index of the relevant ship
     *
     * @param timeIn a Date to search all ships that came before it
     * @return Mono of Int that calculate as index of the relevant ship
     *
     */
    fun getNextIndexInQueue(timeIn: Date): Mono<Int> {
        return this.visitCrud.findAllByShipStatus("WAITING")
            .filter { visit ->
                visit.timeIn!!.before(timeIn)
            }
            .collectList()
            .map { visits ->
                (visits?.size ?: 0) + 1
            }.onErrorResume { Mono.just(1) }
    }

    /**
     * update existing visit, if the visit id that given is not exist then there
     * will be no update. for assign ship for a dock then you need to specify relevant dock id
     * on input
     *
     * @param visit a VisitBoundary with the new details
     * @return Mono of void
     *
     */
    override fun update(visit: VisitBoundary): Mono<Void> {
        return visitCrud.findById(visit.id!!)
            .switchIfEmpty(Mono.error(VisitNotFoundException("Visit with ID ${visit.id} not found.")))
            .flatMap { visitEntity ->
                if (visit.shipStatus == "LEFT" &&  visitEntity.shipStatus != "LEFT") {
//                  the ship is left the port
                    visitEntity.timeOut = Date()
                    visitEntity.shipStatus = "LEFT"
                    dockCrud.findById(visitEntity.dock!!)
                        .flatMap { dockEntity ->
                            if(dockEntity.takenBy.equals(visitEntity.id))
                                dockEntity.takenBy = null
                            dockCrud.save(dockEntity).then(visitCrud.save(visitEntity))
                        }

                } else if (visit.shipStatus == "UNKNOWN" &&  visitEntity.shipStatus != "UNKNOWN" &&  visitEntity.shipStatus != "LEFT") {
                    visitEntity.shipStatus = "UNKNOWN"
                    dockCrud.findById(visitEntity.dock!!)
                        .flatMap { dockEntity ->
                            if(dockEntity.takenBy.equals(visitEntity.id))
                                dockEntity.takenBy = null
                            dockCrud.save(dockEntity).then(visitCrud.save(visitEntity))
                        }
                }else if (!visitEntity.shipStatus.equals("WAITING")) {
                    Mono.empty()
                }else{
//                    assign ship on queue
                    getAvailableDock(visit.dock!!,visitEntity.size!!)
                    .flatMap { dockEntity ->
                        visitEntity.dock = dockEntity.id
                        visitEntity.shipStatus = "ON_DOCK"
                        dockEntity.takenBy = visitEntity.id
                        dockCrud.save(dockEntity)
                            .then(visitCrud.save(visitEntity))
                    }
                }

            }
            .onErrorResume {
                when (it) {
                    is DockNotAvailableException -> {
                        visitCrud.findById(visit.id!!)
                            .flatMap { visitEntity ->
                                visitEntity.shipStatus = "WAITING"
                                visitCrud.save(visitEntity)
                            }
                    }
                    is VisitNotFoundException ->{
                        Mono.error(VisitNotFoundException("Visit with ID ${visit.id} not found."))
                    }
                    else -> Mono.error(it)
                }
            }.then()
    }

    /**
     * retrieve all existing visits filtered and sorted as requested, if the filter value
     * not exist then it will not do any filter.
     *
     * @param filterType a String value, can be a String
     *                  ["byDock", "byType", "ByShipName", "byTimeRange", "byShipId"]
     * @return Flux of VisitBoundary that filtered and sorted as requested.
     * @see getSortBy(Function)
     */
    override fun getVisits(filterType: String, filterValue: String, pageable: PageRequest): Flux<VisitBoundary> {
        return when(filterType) {
            "byDock" -> visitCrud.findAllByDock(filterValue, pageable)
            "byType" -> visitCrud.findAllByShipType(filterValue, pageable)
            "ByShipName" -> visitCrud.findAllByShipName(filterValue, pageable)
            "byTimeRange" -> {
                val from = LocalDateTime.parse(filterValue.split(",")[0], DateTimeFormatter.ofPattern("ddMMyyyyHHmm"))
                val to = LocalDateTime.parse(filterValue.split(",")[1], DateTimeFormatter.ofPattern("ddMMyyyyHHmm"))
                visitCrud.findAllByTimeInBetween(
                    Date.from(from.toInstant(ZoneOffset.UTC)),
                    Date.from(to.toInstant(ZoneOffset.UTC)),
                    pageable)
            }
            "byShipId" -> visitCrud.findAllByShipId(filterValue, pageable)
            "byShipStatus" -> visitCrud.findAllByShipStatus(filterValue, pageable)
            else -> visitCrud.findAll(pageable.sort)
        }.collectList()
            .flatMapMany { visits ->
                val visitsWithIndex = visits.mapIndexed { index, visit ->
                    Pair(index, visit)
                }
                Flux.fromIterable(visitsWithIndex)
                    .flatMap { pair ->
                        val index = pair.first
                        val visit = pair.second
                        setVisitQueue(visit)
                            .map { visitBoundary ->
                                visitBoundary to index
                            }
                    }
                    .collectSortedList(
                        compareBy<Pair<VisitBoundary, Int>> { it.second }
                    )
                    .flatMapMany { sortedVisits ->
                        Flux.fromIterable(sortedVisits)
                            .map { it.first }
                    }
            }

    }
    fun setVisitQueue(visit: VisitEntity): Mono<VisitBoundary>{
        return Mono.just(visit)
            .map{v ->
                if(v.shipStatus != "WAITING") {
                    //          There is no need to assign the index on our queue
                    Mono.just(this.converter.toBoundary(visit))
                }else{
                    Mono.just( this.converter.toBoundary(visit))
                        .zipWith(
                            getNextIndexInQueue(visit.timeIn!!)
                        ).map{tuple->
                            tuple.t1.indexQueue = tuple.t2
                            tuple.t1
                        }
                }
            }.flatMap { it }
    }
    /**
     * find available and suitable dock for the ship, the dock must be bigger from the ship, so it
     * could fit in. firs search is for the dock id is given - if its available and on the right
     * size then we retrieve this dock, otherwise we will search for the next dock that stand for
     * those conditions
     *
     * @param dockId a dock ID that asked to assign for
     * @param shipSize a size of the ship that contain width and length
     *
     * @return Mono of DockEntity that found available and suitable for the ship's size
     *
     */
    fun getAvailableDock(dockId: String, shipSize: Size): Mono<DockEntity> {
        return this.dockCrud.findByidAndTakenByIsNull(dockId)
            .filter { dock ->
                dock.size!!.width!! >= shipSize.width!! && dock.size!!.length!! >= shipSize.length!!
            }
            .switchIfEmpty(this.dockCrud.findByTakenByIsNull()
            .filter { dock ->
                dock.size!!.width!! >= shipSize.width!! && dock.size!!.length!! >= shipSize.length!!
            }
            .next())
            .switchIfEmpty(Mono.error(DockNotAvailableException("No available docks found")))
    }

    override fun getSortOrder(sortOrder: String): Sort.Direction {
        return if (sortOrder.equals("DESC", ignoreCase = true)) {
            Sort.Direction.DESC
        }else {
            Sort.Direction.ASC
        }
    }

    /**
     * retrieve sortAttribute that we support  on sorting
     * @param sortBy a String value, can be ["byShipSizeLength",
     * "byShipSizeWidth, "byTimeIn, "byShipStatus, "byDock, "byShipName], default value ("id")
     * @return valid sort attribute


     */
    override fun getSortBy(sortBy: String,isDock:Boolean): String {
        return when (sortBy) {
            "byShipSizeLength" -> if(!isDock) "size.length" else "Id"
            "byShipSizeWidth" -> if(!isDock) "size.width" else "Id"
            "byTimeIn" -> if(!isDock) "timeIn" else "Id"
            "byShipStatus" -> if(!isDock) "shipStatus" else "Id"
            "byDock" -> if(!isDock) "dock" else "Id"
            "byShipName" -> if(!isDock) "shipName" else "Id"
            "byType" -> {if(isDock) "type" else "Id"}
            "byDockSizeLength" -> if(isDock) "size.length" else "Id"
            "byDockSizeWidth" -> if(isDock) "size.width" else "Id"
            "byTakenBy" -> if(isDock) "takenBy" else "Id"
            else -> "Id"
        }
    }

    override fun createDock(dock: DockBoundary): Mono<DockBoundary> {
        dock.id = null
        dock.takenBy = null

        return Mono.just(dock)
            .log()
            .map { boundary -> this.converter.toEntity(boundary) }
            .flatMap { this.dockCrud.save(it) }
            .map { this.converter.toBoundary(it) }
            .log()
    }

    override fun getSpecificVisit(id: String): Mono<VisitBoundary> {
        return this.visitCrud
            .findById(this.converter.convertIdToEntity(id))
            .flatMap { visit ->
                setVisitQueue(visit)
            }
            .log()
    }

    override fun createVisit(visit: VisitBoundary): Mono<VisitBoundary> {
        visit.id = null
        visit.timeOut = null
        visit.timeIn = Date()
        return getAvailableDock(visit.dock!!, visit.shipSize!!)
            .flatMap { dock ->
                visit.dock = dock.id
                visit.shipStatus = "ON_DOCK"
                visitCrud.save(converter.toEntity(visit))
                    .map { savedVisit ->
                        val boundary = converter.toBoundary(savedVisit)
                        dock.takenBy = savedVisit.id
                        dockCrud.save(dock)
                            .then(
                                Mono.just(boundary)
                            )
                    } .flatMap {
                        it
                    }
            }
            .onErrorResume {
                getNextIndexInQueue(visit.timeIn!!)
                    .flatMap { nextIndex ->
                        visit.dock = null
                        visit.indexQueue = nextIndex
                        visit.shipStatus = "WAITING"
                        visitCrud.save(converter.toEntity(visit))
                            .map { savedVisit ->
                                val boundary = converter.toBoundary(savedVisit)
                                boundary.indexQueue = nextIndex
                                boundary
                            }
                    }
            }
    }
}

