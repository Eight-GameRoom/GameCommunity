FROM openjdk:17-alpine
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} app.jar
COPY ./config/application.properties /src/main/resources/
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.config.location=file:/src/main/resources/application.properties"]
