FROM openjdk:21-bookworm
WORKDIR /app
COPY build/libs/*.jar /app/kufarspy-application.jar
EXPOSE 8888
CMD ["java", "-jar", "kufarspy-application.jar"]