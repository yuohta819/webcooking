# Giai đoạn 1: Build app bằng Maven (image có sẵn Maven)
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Sao chép toàn bộ project vào container
COPY . .

# Build project và bỏ qua test
RUN mvn clean package -DskipTests

# Giai đoạn 2: Chạy app bằng JDK nhẹ hơn
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy file jar đã build từ stage 1
COPY --from=build /app/target/*.jar app.jar

# Mở cổng 8080
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
