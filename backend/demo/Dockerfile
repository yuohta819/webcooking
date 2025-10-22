# Giai đoạn 1: Build app bằng Maven
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Sao chép thư mục demo (chứa pom.xml)
COPY demo /app

# Build project
RUN mvn -f /app/pom.xml clean package -DskipTests

# Giai đoạn 2: Chạy ứng dụng
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy file jar từ build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
