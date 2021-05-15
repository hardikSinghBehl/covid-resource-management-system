FROM adoptopenjdk:15_36-jdk-hotspot
RUN mkdir -p /var/log/covid-resource-management-system
VOLUME /tmp
EXPOSE 8080
ADD target/covid-resource-management-system-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]