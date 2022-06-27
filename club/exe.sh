#!/bin/sh

POINT_DIR=../../TRIPLE_HOMEWORK_POINT
./mvnw clean package
${POINT_DIR}/mvnw clean package

docker-compose up