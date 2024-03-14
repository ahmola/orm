FROM ubuntu:latest AS build
RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .
RUN ./gradlew bootJar --no-daemon
EXPOSE 8080
COPY --from=build /build/libs/orm-0.0.1-SNAPSHOT-plain.jar orm-0.0.1-SNAPSHOT-plain.jar

ENTRYPOINT ["java", "-jar", "orm-0.0.1-SNAPSHOT-plain.jar"]