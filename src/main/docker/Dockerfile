FROM openjdk:21-oracle
ARG JAR_FILE=*.jar
EXPOSE 8181
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
