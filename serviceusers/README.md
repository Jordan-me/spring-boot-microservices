# Getting Started

### How do I get set up?
[Our OpenAPI](http://localhost:8081/swagger-ui/index.html#/)  
clone this repository  
Note that this is subproject so make sure to do build for the project and then for the service you interest for (Users Management service consumer)

#### Data Structure
* User:
```
        User:{
            "email":"user1@s.afeka.ac.il",
            "name":{"firstName":"Catherin", "lastName":"Cloudly"},
            "password":"ab4de6_8",
            "birthdate":"16-11-1958",
            "roles":["manager","admin","employee"]
        }
```

### General details:
Service's name: UsersManagementService
#### URLS
* swagger-ui: http://localhost:8081/swagger-ui/index.html#/
1. `POST /users`    
   An operation that receives data with the details of a user, stores them for future use and returns them back to the service operator
   * If there are already details of a user with the same email address in the system, error code 500 will be returned

2. `GET /users/byEmail/{email}`  
   An operation that returns the information of a user, already stored in the system, that is uniquely identified by the email address

3. `GET /users/login/{email}?password={password}`    
   An action aimed at verifying that the correct password of the user of the service has been entered.

4. `PUT /users/{email}`  
   An operation that updates a user's details, which are already saved in the system and are identified through the email address.

5. `DELETE /users`  
   An operation that deletes all user information in the system and does not return anything

6. `GET /users/search?size={size}&page={page} &sortBy={sortAttribute}&sortOrder={order}`  
   An operation that returns an array of all the users already saved in the service and enables pagination.

7. `GET /users/search?criteriaType=byEmailDomain&criteriaValue={value}&size={size}&page={page}&sortBy={sortAttribute}&sortOrder={order}`  
An operation that returns an array of users whose email address belongs to a certain domain (DOMAIN) passed as a value parameter and enables pagination.
   * criteriaType: {byEmailDomain, byBirthYear, byRole}

#### Sort results with pagination
The optional parameter sortBy defines by which characteristic the sorting will be performed. If no value is entered for it, the results will be sorted by email.  
* Possible values of sortBy include the following strings:
  * email - sorting by user's email address  
  * firstName - sort by user's first name  
  * lastName - sort by user's last name
  * birthdate - sorting by users' date of birth
