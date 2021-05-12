# Covid Resource Management System

##### Submission project for Jagan Institute Of Management Studies, Rohini-5 (JIMS) resource sharing challenge under theme community help during covid-19

## Tech Stack Used 
* Java 15
* Spring Boot
* Spring Security (JWT Based Authentication and Authorization)
* Spring Data JPA/Hibernate
* PostgreSQL PL/pgSQL
* Flyway
* Open-API
* Lombok

## Entities
##### SQL Migration Scripts can be viewed at src/main/resources/db/migration
![ER Diagram Of Entities](https://i.ibb.co/bL7Vb7g/Deepin-Screenshot-select-area-20210511195800.png)

## Security Flow
* On Successful validation of login credentials, a JWT will be returned representing the user **(decode the below sample JWT on jwt.io for reference)**

```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoYXJkaWsuYmVobEB0aGVsYXR0aWNlLmluIiwiYWNjb3VudF9jcmVhdGlvbl90aW1lc3RhbXAiOnsieWVhciI6MjAyMSwibW9udGhWYWx1ZSI6NSwiZGF5T2ZNb250aCI6MTEsImhvdXIiOjIwLCJtaW51dGUiOjM1LCJzZWNvbmQiOjU1LCJuYW5vIjo2MDE4MDAwMDAsIm1vbnRoIjoiTUFZIiwiZGF5T2ZXZWVrIjoiVFVFU0RBWSIsImRheU9mWWVhciI6MTMxLCJjaHJvbm9sb2d5Ijp7ImNhbGVuZGFyVHlwZSI6Imlzbzg2MDEiLCJpZCI6IklTTyJ9fSwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJ1c2VyX2lkIjoiZWRlOThjZDAtYWFiZC00MDMzLTgwZmYtMWUxMWIxOTg3MDY1Iiwic2NvcGUiOiJ1c2VyIiwibmFtZSI6IkhhcmRpayBCZWhsIiwiZXhwIjoxNjIwNzgxNTgwLCJpYXQiOjE2MjA3NDU1ODAsImNvbnRhY3RfbnVtYmVyIjoiOTk5OTE1NTc4NiJ9.ktBeEMM4_FU-OLFJ6hxhLVqlYtMoPa0qZY2rjC8obic
```
* The received JWT should be included in the headers when calling a protected API
* Authentication Bearer format to be used **(Header key should be 'Authentication' and value should start with Bearer followed with a single blank space and recieved JWT)**

```
Authentication : Bearer <JWT>
```

## Main Features
* User's able to register them with the application (register/login/update-details/change-password)
* Submit a request for a resource **(refer master_resource_types)** (create/update-details/mark-inactive)
* Register a resource with the application (cretae/update-details/update-count/mark-inactive)
* Users can comment on the following items (request, resource, comment)
* Users can report the following items (request, resource, comment)
* Itmems with >10 reports will be automatically removed (soft-delete) through cron job schedulers
* Users can follow other users for notifications **(TODO-mail-notification)**
* Users gain credibility points whenever they assist with a request fulfillment
* List of Nearest Requests based on the users location is returned
* List of Nearest Resources based on the users location is returned

## Nearest Resource/Request 

[LINK 1](https://github.com/hardikSinghBehl/covid-resource-management-system/blob/main/src/main/java/com/hardik/pomfrey/repository/RequestRepository.java)

[LINK 2](https://github.com/hardikSinghBehl/covid-resource-management-system/blob/main/src/main/java/com/hardik/pomfrey/repository/ResourceRepository.java)

Example repository layer returning list of nearest requests relative to users location (latitude/longitude taken during registration, can be updated) 

```
SELECT id , latitude, longitude, SQRT(
    POW(69.1 * (latitude - [users-latitude]), 2) +
    POW(69.1 * ([users-longitude] - longitude) * COS(latitude / 57.3), 2)) AS distance
FROM requests WHERE is_active = true ORDER BY distance DESC;
```

## TODO
* Unit Tests using Junit/Mockito/MockMVC
* Integration Tests Using TestContainers/TestRestTemplate
* ~~Scheduler For Removing Bulk Reported Items (Request/Resource/Comment)~~
* Integrate Java-Mail-API/Freemarker for event notifications
* Create POC using Postgis Geometry for storing location instead of latitude/longitude

## Setup

* Install Java 15
* Install Maven

Recommended way is to use [sdkman](https://sdkman.io/) for installing both maven and java

Run mvn clean install in the core

```
mvn clean install
```

Run docker commands

```
sudo docker-compose build
sudo docker-compose up -d
```

Service port is 9090 and Postgres Port is 6432. They both can be changed in the [docker-compose.yml](docker-compose.yml) file

To stop the container run

```
sudo docker-compose stop
```

## To Run Locally Without Docker

Create postgres user (superuser) with name and password as pomfrey

```
CREATE USER pomfrey WITH PASSWORD 'pomfrey' SUPERUSER;
```
Run mvn clean install in the core 

```
mvn clean install
```
Run Application 

```
run as -> spring boot application
```

API Documentation can be viewed by visiting the below link (can be altered in application.properties)

```
http://localhost:9091/pomfrey/swagger-ui.html
```


