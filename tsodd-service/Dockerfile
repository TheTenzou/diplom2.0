FROM gradle:6.8.3-jdk11 as builder
USER root
WORKDIR /builder
ADD . /builder
RUN gradle build --stacktrace -x test

FROM openjdk:11
WORKDIR /app
COPY --from=builder /builder/build/libs/tsodd-service.jar .
COPY wait-for.sh .

# install netcat
# netcat is used by wait-for.sh
RUN apt update
RUN apt install -y netcat