FROM openjdk:17 as build
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.config.location=file:/src/main/resources/application.properties"]
