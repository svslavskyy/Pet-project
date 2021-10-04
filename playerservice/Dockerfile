FROM alpine
RUN apk update && apk add postgresql-client
CMD until pg_isready --username=postgres --host=postgres; do sleep 1; done \
    && psql --username=postgres --host=postgres --list

FROM maven:3.6.3-jdk-11 AS builder
WORKDIR /workdir/playerservice
COPY pom.xml /workdir/playerservice/pom.xml
RUN mvn dependency:go-offline

COPY src /workdir/playerservice/src
RUN ["mvn", "install", "-Dmaven.test.skip=true"]
RUN mkdir  -p target/dependency
WORKDIR /workdir/playerservice/target/dependency
RUN jar -xf ../*.jar

FROM openjdk:11-jre-slim

EXPOSE 8080
VOLUME /tmp
ARG DEPENDENCY=/workdir/playerservice/target/dependency
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=builder ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","tech.pm.edu.PlayerServiceApplication"]