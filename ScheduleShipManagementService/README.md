# Getting Started

### How do I get set up?
For client Rsocket command line install [RSocket client](https://github.com/making/rsc/releases)    
[Consumer Reactive Service](../ScheduleShipManagementServiceConsumer/README.md)    
clone this repository  
Note that this is subproject so make sure to do build for the project and then for the service you interest for (Schedule Ship Management service)  
  
Before you running the service it is important that you will upload mongo image from docker, this subproject will create scheduleshipsdb as database and DOCKS & VISITS collections inside it.  
  
  
### General details:
Service's name: ScheduleShipManagementService
Service's consumer name: ScheduleShipManagementServiceConsumer
#### Data Structure
* Dock:
```
    Dock:{
        "id":  (int) auto-increment, //dock number
        "Type" : (string), // e.g. <"park lot", "docking">
        "taken_by": uuid, //visit’s  id, if its null than place available
        "Size": {"width": 250.0, "length": "3.0"} // given float
    }
```

* Visit:
```
    visit:{
        "id":"18a6ba3f9938d",
        "shipId": "123456789",
        "shipName": "Blue sea",
        "dock": id, //dock's id 
        "index_queue": index,
        "Time_in": (timeStamp),
        "Time_out": (timeStamp),
        "shipType ": " oil " ,
        "shipStatus ":"WAITING/ ON_DOCK/ LEFT/ UNKNOWN",
        "shipSize": {"width":5.0 (float), "length": 20.0 (float)},
        "Weight_tons ":20.5 (float),
        "load" :{
            "number_of_containers": 2,
            "containerID01":
            {
                Container_size: 20,
                Load_type : "liquid"
            },
            “containerID02”:{
                Container_size: 40,
                 Load_type : "liquid"
            }
        }
    }

```