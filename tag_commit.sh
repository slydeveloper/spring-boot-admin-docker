#!/bin/bash

source .env
git tag -a v$DOCKER_IMAGE_VERSION -m "Add version $DOCKER_IMAGE_VERSION"
git push --tags