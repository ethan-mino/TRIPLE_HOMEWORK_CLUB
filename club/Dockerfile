FROM adoptopenjdk/openjdk11
ARG JAR_FILE_PATH=target/*.jar
COPY ${JAR_FILE_PATH} club.jar
ENTRYPOINT ["java", "-jar", "club.jar"]