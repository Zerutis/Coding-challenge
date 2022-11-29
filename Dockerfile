FROM openjdk:19
ADD target/coding-challenge-app.jar coding-challenge-app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "/coding-challenge-app.jar"]