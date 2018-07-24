#!/bin/bash

set -ex

USERNAME=slydeveloper
IMAGE=spring-boot-admin

# build JAR package
mvn clean package -Dmaven.test.skip=true

# build Docker image
docker build -t $USERNAME/$IMAGE:latest .