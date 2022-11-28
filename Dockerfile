FROM openjdk:19
ADD target/coding-challenge-webapp.jar coding-challenge-webapp.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "coding-challenge-webapp.jar"]