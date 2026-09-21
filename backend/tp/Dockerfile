FROM maven:3-eclipse-temurin-25
WORKDIR /app
COPY . .
RUN mvn -q clean package -DskipTests
CMD ["sh", "-c", "java -jar target/*.jar"]