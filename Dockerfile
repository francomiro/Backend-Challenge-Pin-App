FROM openjdk:17-jdk-slim

ENV SPRING_DATASOURCE_URL jdbc:mysql://mysql:3306/clientdb
ENV SPRING_DATASOURCE_USERNAME root
ENV SPRING_DATASOURCE_PASSWORD admin
ENV SPRING_LOGGING.LEVEL INFO

WORKDIR /app

COPY apicompilada/challenge-0.0.1-SNAPSHOT.jar /app/app.jar
COPY wait-for-it.sh /app/wait-for-it.sh

EXPOSE 8080

ENTRYPOINT ["/app/wait-for-it.sh", "db:3306", "--", "java", "-jar", "/app/app.jar"]