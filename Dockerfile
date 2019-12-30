FROM java:8
EXPOSE 8080
ADD /target/HarCRUD-1.0-SNAPSHOT.jar HarCRUD-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","HarCRUD-1.0-SNAPSHOT.jar"]