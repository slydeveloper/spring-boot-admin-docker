#!/bin/bash

# stop running container
docker stop spring-boot-admin

# remove old container
docker rm spring-boot-admin

# run container
docker run -d -p 1111:1111 --name spring-boot-admin slydeveloper/spring-boot-admin:latest