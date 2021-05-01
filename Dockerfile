FROM openjdk:11-jre-slim-buster
WORKDIR /app
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]