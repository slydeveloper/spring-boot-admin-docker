#!/bin/bash

set -ex

USERNAME=slydeveloper
IMAGE=spring-boot-admin
VERSION=1.0

# build JAR package
mvn clean package

# build Docker image
docker build -t $USERNAME/$IMAGE:latest .

# tag image
docker tag $USERNAME/$IMAGE:latest $USERNAME/$IMAGE:$VERSION

# push images
docker push $USERNAME/$IMAGE:latest
docker push $USERNAME/$IMAGE:$VERSION

