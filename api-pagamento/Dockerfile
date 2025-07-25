FROM gradle:8.0.2-jdk17 AS build
WORKDIR /app
COPY ./src/main/java/destaxa/apipagamento /app/src/main/java/destaxa/apipagamento
COPY ./src/main/resources /app/src/main/resources
COPY ./settings.gradle /app/
COPY ./api-pagamento-docker.properties /app/src/main/resources/application-docker.properties

# Create build.gradle file
RUN echo 'plugins {\n    id "org.springframework.boot" version "3.2.0"\n    id "io.spring.dependency-management" version "1.1.4"\n    id "java"\n}\n\ngroup = "destaxa"\nversion = "0.0.1-SNAPSHOT"\nsourceCompatibility = "17"\n\nrepositories {\n    mavenCentral()\n}\n\ndependencies {\n    implementation "org.springframework.boot:spring-boot-starter-web"\n    implementation "org.springframework.boot:spring-boot-starter-validation"\n    implementation "org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0"\n    implementation "org.projectlombok:lombok"\n    annotationProcessor "org.projectlombok:lombok"\n    testImplementation "org.springframework.boot:spring-boot-starter-test"\n}\n\ntasks.named("bootJar") {\n    mainClass = "destaxa.apipagamento.ApiPagamentoApplication"\n}' > /app/build.gradle

# Build the application
RUN gradle bootJar --no-daemon --info

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]