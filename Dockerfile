FROM openjdk:17-alpine
WORKDIR /app
COPY target/mailserverapp-1.0.0.jar .
EXPOSE 8484
CMD ["java","-jar","mailserverapp-1.0.0.jar"]
