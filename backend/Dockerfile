ARG JAVA_VERSION_TAG=17

FROM eclipse-temurin:${JAVA_VERSION_TAG}-jdk as developement
RUN mkdir /usr/app 
WORKDIR /usr/app
CMD ["./mvnw", "spring-boot:run"]