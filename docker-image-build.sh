#!/bin/bash

set -ex

USERNAME=slydeveloper
IMAGE=spring-boot-admin

# build JAR package
mvn clean package

# build Docker image
docker build -t $USERNAME/$IMAGE:latest .