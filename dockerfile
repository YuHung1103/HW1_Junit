FROM openjdk:17

ADD target/HW1-3.1.1.jar HW1-3.1.1.jar

EXPOSE 8080

CMD ["java", "-jar", "/HW1-3.1.1.jar"]