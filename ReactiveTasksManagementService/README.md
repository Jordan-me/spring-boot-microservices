# Getting Started

### How do I get set up?
[Our OpenAPI](http://localhost:8082/webjars/swagger-ui/index.html#/)  
clone this repository  
Note that this is subproject so make sure to do build for the project and then for the service you interest for (Tasks Management service consumer)

#### Data Structure
* Task:
```
      Task:{
         "taskId":"18a6ba3f9938d",
         "subject":"Deliver Reactive Tasks Microservice",
         "categories":["mandatory exercise", "development", "team work"],
         "createdTimestamp":"2022-11-30T17:14:32.107+0000",
         "creator":"camila@cloud.afeka.ac.il",
         "associatedUsers":[
          "jane@s.afeka.ac.il", "jill@gmail.com",
          "claudia@yahoo.com", "shanshan@outlook.com"
         ],
         "status":"INITIATED",
         "details":{
          "history":[
             {
               "phase":"project initiation",
               "init":"2022-11-30T17:14:32.107+0000"
             }
           ],
           "programmingLanguage":"Kotlin",
           "expectedDelivery":"2022-12-14",
           "estimatedTimeToDeliver":"TBD"
         }
      }
```

### General details:
Service's name: TasksManagementService  
#### URLS
* swagger-ui: http://localhost:8082/webjars/swagger-ui/index.html#/
1. `POST /tasks  `  
   An operation that receives new task details, sets its creation date and its identifier and stores it.
   This action will return the task details reactively.

2. `GET /tasks/{taskId}  `  
   An operation that returns a certain task according to its identifier value in a reactive way. If there is no such task in the system, the operation will return an empty Mono

3. `GET /tasks?sortBy={sortAttr}&sortOrder={order}&page={page}&size={size}  `  
   An operation that reactively returns all the tasks that exist in the service, with the help of PAGINATION, sorted according to the parameters passed in the URL.
   Pay attention to the notes about sorting and pagination of the results later in the exercise.

4. `GET /tasks?filterType=bySubject&filterValue={taskSubject}&sortBy={sortAttr}&sortOrder={order}&page={page}&size={size} `  
   An action that reactively returns the tasks whose subject was passed as a parameter. Pay attention to the notes about sorting and pagination of the results later in the exercise.

5. DELETE /tasks  
   An operation that deletes all data that the service manages and returns a Mono of Void

#### Search Example
* `GET /tasks?filterType=byCreatorEmail&filterValue={email}&sortBy={sortAttr}&sortOrder={order}&page={page}&size={size}`  
  An action that reactively returns the tasks created by a user whose email was passed as a parameter. Pay attention to the notes about sorting and pagination of the results later in the exercise.
* ``GET /tasks?filterType=byStatus&filterValue={status}&sortBy={sortAttr}&sortOrder={order}&page={page}&size={size}``  
  An operation that reactively returns the tasks that have a status passed as a parameter. Pay attention to the notes about sorting and pagination of the results later in the exercise.