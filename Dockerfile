FROM openjdk:27-ea-trixie
LABEL authors="Rishabh"
ADD target/webchat.jar webchat.jar
ENTRYPOINT ["java","-jar","/webchat.jar"]


