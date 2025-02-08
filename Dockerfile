FROM openjdk:17-jdk-slim

ENV SPRING_DATASOURCE_URL jdbc:mysql://database-1.chwkemuc6vzv.us-east-2.rds.amazonaws.com:3306/clientdb
ENV SPRING_DATASOURCE_USERNAME admin
ENV SPRING_DATASOURCE_PASSWORD pinappclave
ENV SPRING_LOGGING.LEVEL INFO

WORKDIR /app

COPY apicompilada/challenge-0.0.1-SNAPSHOT.jar /app/app.jar
COPY wait-for-it.sh /app/wait-for-it.sh

EXPOSE 8080

ENTRYPOINT ["/app/wait-for-it.sh", "db:3306", "--", "java", "-jar", "/app/app.jar"]