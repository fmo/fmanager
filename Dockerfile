FROM openjdk:17-jdk-slim

ENV JAVA_OPTS " -Xms512m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

WORKDIR application

COPY ./target/fmanager-0.0.1-SNAPSHOT.jar ./

ENTRYPOINT ["java", "-jar", "fmanager-0.0.1-SNAPSHOT.jar"]