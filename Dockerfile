FROM maven:3.8.4-openjdk-17 as builder
WORKDIR /
COPY . /app/.
COPY db_proj/create_db.sql /docker-entrypoint-initdb.d/.
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip=true

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/*.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/*.jar"]