FROM openjdk:17

# Sao chép file JAR vào container
COPY target/*.jar app.jar

# Chạy ứng dụng Java
ENTRYPOINT ["java", "-jar", "app.jar"]


# vi moi lan sua doi gi thi no lai chay lai nen ton dung luong , vay nen xay dung them 1 file la dokerfile layerd