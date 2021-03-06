# Housing Utility Cost Accounting System

The project calculate cost for all housing utilities. 
The project was developed using microservice architecture, so for each utility one service is used. 
The amount of resources spent is generated by the counter and sent to all services using the Kafka message broker every day. 
For security, OAuth2 auth server is used.

## Technologies
Project is created with:
* Java 8
* Spring Boot 2.2.2.RELEASE
* Spring Cloud Hoxton.SR1
* Netflix Zuul
* Netflix Eureka
* Spring Security
* Spring Data
* Apache Kafka 2.4.0
* Docker version 19.03.8
* React Js 16.13+
* MySQL 8.0
* Maven 3.5+
* Git 2+

## Run
To run this project, install docker and docker compose.
* [Install Docker on Linux](https://docs.docker.com/install/linux/docker-ce/ubuntu/)
* [Install Docker on Windows](https://docs.docker.com/docker-for-windows/install/)
* [Install Docker Compose](https://docs.docker.com/compose/install/)

Copy [docker-compose.yml](https://github.com/maksimk99/housing-utilities/blob/master/docker/docker-compose.yml) file, 
then go to the directory with this file and run following command in console:
```
docker-compose up
```
