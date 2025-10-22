# =========================
# Giai đoạn 1: Build app bằng Maven
# =========================
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Sao chép toàn bộ mã nguồn vào container
COPY demo /app

# Build project và bỏ qua test
RUN mvn -f /app/pom.xml clean package -DskipTests


# =========================
# Giai đoạn 2: Chạy ứng dụng
# =========================
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy file JAR từ stage build sang
COPY --from=build /app/target/*.jar app.jar

# ✅ Copy file credentials vào container
COPY demo/newagent-9mjr-e07525211246.json /app/newagent-9mjr-e07525211246.json

# ✅ Đặt biến môi trường
ENV GOOGLE_APPLICATION_CREDENTIALS="/app/newagent-9mjr-e07525211246.json"

# Mở cổng 8080
EXPOSE 8080

# ✅ Lệnh khởi chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
