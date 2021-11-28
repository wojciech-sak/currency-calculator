FROM openjdk:11
COPY build/libs/currency-calculator-1.0.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080