FROM alpine
RUN apk update && apk add postgresql-client
CMD until pg_isready --username=postgres --host=postgres; do sleep 1; done \
    && psql --username=postgres --host=postgres --list

FROM maven:3.6.3-jdk-11 AS builder
WORKDIR /workdir/games-repository
COPY pom.xml /workdir/games-repository/pom.xml
RUN mvn dependency:go-offline

COPY src /workdir/games-repository/src
RUN ["mvn", "install", "-Dmaven.test.skip=true"]
RUN mkdir  -p target/depency
WORKDIR /workdir/games-repository/target/dependency
RUN jar -xf ../*.jar

FROM openjdk:11-jre-slim

EXPOSE 8080
VOLUME /tmp
ARG DEPENDENCY=/workdir/games-repository/target/dependency
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=builder ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","tech.pm.edu.GamesRepository"]