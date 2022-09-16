FROM maven:3.8.5 AS maven
WORKDIR /usr/src/app
COPY ./ ./
RUN mvn package

FROM openjdk:17.0.2
ARG JAR_FILE=personalitytest.jar
WORKDIR /opt/app
COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/
ENTRYPOINT ["java", "-jar", "personalitytest.jar"]