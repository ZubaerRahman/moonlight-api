# Base image
FROM gradle:jdk11-jammy
	
# Set the working directory
WORKDIR /app

# Copy the build.gradle and settings.gradle files to the container
COPY build.gradle settings.gradle gradle/ gradlew ./

# Copy the source code to the container
COPY src/ src/

RUN pwd

RUN ls

RUN ls -al

RUN cat ./gradlew

RUN chmod +x ./gradlew

RUN gradle wrapper

# Run the Gradle build command to build the application
RUN ./gradlew build --no-daemon --stacktrace --info

# Copy the built JAR file to the container
COPY build/libs/moonlight-0.0.1-SNAPSHOT.jar /app

# Expose port 8080
EXPOSE 8080

# Set the command to run the JAR file
CMD ["java", "-jar", "/app/moonlight-0.0.1-SNAPSHOT.jar"]