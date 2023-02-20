# Getting Started

### How do I get set up?
[Our OpenAPI](http://localhost:8084/webjars/swagger-ui/index.html#/)
clone this repository
Note that this is subproject so make sure to do build for the project and then for the service you interest for (Schedule Ship Management service consumer)

* Before you running this service you need to run the [Producer RSocket Service](../ScheduleShipManagementService/README.md)


### General details:
Service's name: ScheduleShipManagementService
Service's consumer name: ScheduleShipManagementServiceConsumer
#### URLS
* swagger-ui: http://localhost:8084/webjars/swagger-ui/index.html#/
1. POST/dock
    accept new dock and return the dock saved on DOCKS collection reactively.
   * [Dock Structure](../ScheduleShipManagementService/README.md)
   
2. GET/dock?sortBy={sortAtt}&sortOrder={order}&page={page}&size={size}
    returns all the saved docks on DOCKS collection reactively. If there are no docks empty array will return.

3. POST/visit
    accept new Visit. 
   * If the details of a specific dock {dockId} to which you are requested to enter are defined, the service will check if the dock is free = if so, the ship will be entered directly into the dock and will not wait in line.
   * If no specific dock details are entered in the request or if the dock is not available, the service will search for a dock for the ship automatically - when none is found, the service will put the ship on hold in the queue of ships managed in the system.
   * If there is a free dock, the operation will assign the dock in the visit details. This action returns updated visit details reactively including the position of the ship in the queue.
   
4. GET/visit/{visitId}
   returns a certain visit according to its identifier value in a reactive way. If there is no such visit in the system, the operation will return an empty Mono.
5. PUT/visit/{visitId}
   updates the details of a visit, which is already saved in the system and is identified by its ID. This also takes a json of the updated visit details and returns nothing.

   * If there is no visit, the method will return an appropriate error code
6. GET/visit?sortBy={sortAttribute}&sortOrder={order}&page={page}&size={size}
   reactively returns all visits.
7. GET/visit?filterType=byDock&filterValue={dockId}&sortBy={sortAttribute}&sortOrder={order}&page={page}&size={size}
   reactively returns all visits according to a certain platform.
8. GET/visit?filterType=byType&filterValue={typeValue}&sortBy={sortAttribute}&sortOrder={order}&page={page}&size={size}
   reactively returns all visits by ship type.
9. GET/visit?filterType=ByShipName&filterValue={shipName}&sortBy={sortAttribute}&sortOrder={order}&page={page}&size={size}
   An action that reactively returns all visits by ship name.
10. GET/visit?filterType=byTimeRange&from={ddMMyyyyHHmm}&to={ddMMyyyyHHmm}&sortBy={sortAttribute}&sortOrder={order}&page={page}&size={size}
    reactively returns all existing visits within a certain time frame.
11. GET/visit?filterType=byShipId&filterValue={shipId}&sortBy={sortAttribute}&sortOrder={order}&page={page}&size={size}
    reactively returns all existing visits by ship ID.

#### Pagination
The sortBy parameter defines by which characteristic the sorting will be performed. If no value is entered for it, the results will be sorted by visitId.
* shipName
* dock
* time_in
* shipStatus
* shipSizeLength
* shipSizeWidth

An example of sorting according to the length of the ship:
    GET/visit?sortBy=byShipSizeLength&sortOrder={order}&page={page}&size={size}


An example of sorting according to the width of the ship:
    GET/visit?sortBy=byShipSizeWidth&sortOrder={order}&page={page}&size={size}

In a GET/dock request, the sorting value can be dockId (default value) SizeLength, SizeWidth, taken_by, Type
An example of sorting by length:
    GET/dock?sortBy=bySizeLength&sortOrder={order}&page={page}&size={size}
An example of sorting by the width:
    GET/dock?sortBy=bySizeWidth&sortOrder={order}&page={page}&size={size}
* If the optional sortOrder parameter is not specified, the sorting will be in ascending order. The value of the parameter can be one of these values:

  * ASC - ascending order

  * DESC - descending order

- The size parameter defines the maximum number of results that will be returned. If no value is entered for it, its value will be 10.
- The optional page parameter defines from which virtual page of results the data should be returned. If no value is entered, its value will be 0.
#### Extra Information:

visitId: A text string that serves as a single-valued visit identifier is represented by a text string that the service generates when invoked with a POST request.

shipId: A string that represents the unique identifier of the ship that arrived at the port.

shipName: A string that represents the name of the ship that arrived at the port.

dock: A text string that is used as a single-valued identifier of a dock where the ship should arrive - if it is null, it means that the ship is waiting in line.

index_queue: A number representing the position of the ship in the queue (if it is indeed in the queue), this number is calculated in the system service and is not saved in the DB, but only exported to the client as output.

Time_in: Date and time signature representing when the ship arrived in port.

Time_out: Date and time signature representing when the ship left port.

shipType: A string representing the type of ship visiting the port.

shipStatus: A string showing the status of the ship visit. can be one of these values:
- WAITING - When you define a new visit using the POST request, the status will be WAITING if there is no free dock where the ship can enter.

- ON DOCK - When a ship is at the dock, the status will change to ON DOCK when the ship leaves the queue and a dock has become available. (in the POST visit request if there is a free dock or in the PUT visit request when a dock is assigned to the ship's visit)

- LEFT - When a ship has left the port, will be changed by PUT visit request.

- UNKNOWN - when the status of the ship's visit is unknown (departure without notice, etc.).

shipSize: An object representing the length and width of the ship.

weight_tons: A decimal number representing the weight of the ship in tons.

load: An object representing the ship's cargo - all container details in the object can be accessed by its identifier and the size and type of the container can be revealed.