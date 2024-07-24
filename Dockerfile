FROM amazoncorretto:17-alpine3.17

COPY target/*.jar application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]