Spring Boot Admin
========================
Yet another implementation of containerized [spring-boot-admin](https://github.com/codecentric/spring-boot-admin)

Docker Image
------------
[![Build Status](https://travis-ci.org/slydeveloper/spring-boot-admin-docker.svg?branch=master)](https://travis-ci.org/slydeveloper/spring-boot-admin-docker)<br />
[![](https://images.microbadger.com/badges/image/slydeveloper/spring-boot-admin.svg)](https://microbadger.com/images/slydeveloper/spring-boot-admin "Get your own image badge on microbadger.com")<br />
[![](https://images.microbadger.com/badges/version/slydeveloper/spring-boot-admin.svg)](https://microbadger.com/images/slydeveloper/spring-boot-admin "Get your own version badge on microbadger.com")<br />
[![Docker Pulls](https://shields.beevelop.com/docker/pulls/slydeveloper/spring-boot-admin.svg)](https://hub.docker.com/r/slydeveloper/spring-boot-admin/)

Info
----
- Name: `slydeveloper/spring-boot-admin`
- Version: `latest`,`1.2`,`1.1`,`1.0`
- [Docker Hub](https://hub.docker.com/r/slydeveloper/spring-boot-admin/)

Details
--------
- Image based on `java:8-jre-alpine`
- Spring Boot Admin version: `2.2.2`
- Spring Boot version: `2.2.6.RELEASE`
- Default port: `1111`
- Default user: `admin`
- Default password: `secret`
- URL: `http://localhost:1111`
- Health check URL - `http://localhost:1111/health`

Usage
--------
`docker run -d -p 1111:1111 --name spring-boot-admin slydeveloper/spring-boot-admin:latest`

Configuration via environment variables 
---------------------------------------
* `SPRING_BOOT_ADMIN_USER_NAME=user`
    * set username: `user`
* `SPRING_BOOT_ADMIN_USER_PASSWORD=password`
    * set password: `password`
* `SPRING_BOOT_ADMIN_TITLE=test`
    * set Page-Title: `test`
* `SPRING_BOOT_ADMIN_SECURITY_ENABLED=false`
    * disable login form (default : `true`)

##### Examples
* `docker run -d -p 1111:1111 -e SPRING_BOOT_ADMIN_TITLE='SB Admin' -e SPRING_BOOT_ADMIN_SECURITY_ENABLED=false --name spring-boot-admin slydeveloper/spring-boot-admin:latest`
* `docker run -d -p 1111:1111 -e SPRING_BOOT_ADMIN_USER_NAME=user -e SPRING_BOOT_ADMIN_USER_PASSWORD='password' --name spring-boot-admin slydeveloper/spring-boot-admin:latest`

Configuration via properties file
---------------------------------
A container supports configuration via *.properties file, just like regular Spring Boot application.
Please note that environment variables will be override by properties file.
Properties of Spring Boot Admin: http://codecentric.github.io/spring-boot-admin/2.1.6/#spring-boot-admin-server

Example `application-docker.properties` file:
```
# Spring Boot server port
server.port=2222

# Spring Boot Admin user/uassword
spring.security.user.name=user
spring.security.user.password=password

# Spring Boot Admin title
spring.boot.admin.ui.title=Custom title

# custom property for disable security
spring.boot.admin.security.enabled=false
```

Example command:
- `docker run -d -p 2222:2222 -v "$(pwd)"/application-docker.properties:/opt/spring-boot-admin-docker/application-docker.properties --name spring-boot-admin slydeveloper/spring-boot-admin:latest`

Docker-Compose example
----------------------
Health check usage of `slydeveloper/spring-boot-admin` with Docker-Compose.
Full working Spring Boot Admin client [here](https://github.com/slydeveloper/spring-boot-admin-example).
```
version: '2.1'

services:
  example:
    image: <SOME_SPRING_BOOT_ADMIN_CLIENT_IMAGE>
    ports:
      - 8080:8080
    depends_on:
        admin:
          condition: service_healthy
    container_name: spring_boot_admin_example
  admin:
    image: slydeveloper/spring-boot-admin
    environment:
      - SPRING_BOOT_ADMIN_TITLE=Custom Spring Boot Admin title
    volumes:
      - ./application-docker.properties:/opt/spring-boot-admin-docker/application-docker.properties
    ports:
      - 1111:1111
    healthcheck:
      test: "curl -sS http://localhost:1111/health"
      interval: 1s
      timeout: 60s
      retries: 120
    container_name: spring_boot_admin_docker
```

Links
-----
- Spring Boot Admin documentation: http://codecentric.github.io/spring-boot-admin/2.2.2/
