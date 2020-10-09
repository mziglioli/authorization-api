# authorization-api

## Overview
This is a restful service for which provides user and authorities

## Runbook
 ```
TODO
 ```
## Server info
spring boot 2 reactive

## Requirements
1. java13

## Installation
    
1. Clean and Build project
    ```
    ./gradlew clean build
    ```

1. Start application via BootRun. There shouldn't be any changes needed for your local environment.
    ```
    ./gradlew bootRun
    ```

## Running locally


### Database setup
There is a docker container for the mongo database when you are running locally. 
Run the following in the project/mongo_db directory:
```
docker-compose up -d
```
bash into the db
```
$ docker exec -it authorization-mongo bash
```
log into db
``` 
mongo -u username_here -p secret_here
```
check db
``` 
use authorization-db
show collections
....
```

### run the app via IntelliJ "Spring Boot configuration"
```
main class: com.mz.authorization.AuthorizationApplication
```

to auto Run an INIT db command add into VM options
```
-Dspring.profiles.active=dev
```

## Api Docs
TODO
- /v3/api-docs
- /swagger-ui.html

### Monitoring
TODO

## Automation Test
TODO
