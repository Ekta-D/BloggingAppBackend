FROM openjdk:22-jdk-alpine
ARG JAR_FILE=target/*jar
COPY ./target/product-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT["JAVA", "-jar","/app.jar"]
