FROM maven:3.8.1-jdk-11 as builder
USER root
WORKDIR /builder
ADD . /builder
RUN mvn package -Dmaven.test.skip=true -q

FROM openjdk:11
WORKDIR /app
COPY --from=builder /builder/target/uds-service.jar .
COPY wait-for.sh .

# install netcat
# netcat is used by wait-for.sh
RUN apt update
RUN apt install -y netcat