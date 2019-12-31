FROM openjdk:8u201-jdk-alpine
MAINTAINER ethan Won <sumusb34@gmail.com>
ADD /build/libs/watni.jar /app/watni.jar
EXPOSE 8080
CMD ["java","-jar","app/watni.jar"]