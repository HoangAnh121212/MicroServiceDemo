FROM openjdk:17

# Sao chép file JAR vào container
COPY target/*.jar app.jar

# Chạy ứng dụng Java
ENTRYPOINT ["java", "-jar", "app.jar"]
