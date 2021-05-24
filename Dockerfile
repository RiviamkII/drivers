# # select parent image
# FROM maven:3.6.3-jdk-11
 
# # copy the source tree and the pom.xml to our new container
# COPY ./ ./
 
# # package our application code
# RUN mvn clean package
 
# # set the startup command to execute the jar
# CMD ["java", "-jar", "target/drivers-0.0.1-SNAPSHOT.jar"]


FROM openjdk:11.0.11-jdk
 
# copy the packaged jar file into our docker image
COPY target/drivers-0.0.1-SNAPSHOT.jar /drivers.jar
 
# set the startup command to execute the jar
CMD ["java", "-jar", "/drivers.jar"]
