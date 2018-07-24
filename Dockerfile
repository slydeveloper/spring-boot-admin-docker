FROM java:8-jre-alpine

MAINTAINER Sylwester Sokolowski <sylwek.sokolowski@gmail.com>

RUN apk add --no-cache curl

COPY target/spring-boot-admin-docker-*.jar /opt/spring-boot-admin-docker/app.jar

COPY application-docker.properties /opt/spring-boot-admin-docker/application-docker.properties

EXPOSE 1111

WORKDIR /opt/spring-boot-admin-docker

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker","-jar","app.jar"]

