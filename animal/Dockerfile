FROM eclipse-temurin:17-jdk AS build

WORKDIR /workspace
COPY . /workspace/app

WORKDIR /workspace/app
RUN chmod +x gradlew && ./gradlew clean bootJar

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/app/build/libs/*.jar app.jar

EXPOSE 8080
CMD ["sh", "-c", "java -jar app.jar --spring.profiles.active=prod --server.port=${PORT:-8080}"]
