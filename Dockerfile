# Use maven 3.3.9 image to build java code
FROM maven:3.3.9 AS builder
# Set working directory to wise-laboratory
WORKDIR /admin-system-vue
# Copy source code to working directory
COPY . .
# Run maven package command
RUN mvn package

# Use java 8 image to run jar file
FROM openjdk:8-jre-alpine AS Release
# Set working directory to /app
WORKDIR /app
# Copy jar file from builder stage to working directory
COPY --from=builder /admin-system-vue/target/admin-system-vue-0.0.1-SNAPSHOT.jar .
# Expose port 8080
EXPOSE 8888
# Run jar file with java command
CMD ["java", "-jar", "admin-system-vue-0.0.1-SNAPSHOT.jar"]