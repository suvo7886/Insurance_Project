FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} insure-me.jar
ENTRYPOINT ["java","-jar","/insure-me.jar"]
