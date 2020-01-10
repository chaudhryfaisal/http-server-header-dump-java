# Use the official maven/Java 8 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM maven:3.5-jdk-8-alpine as builder
ARG TARGET_JAR=/app/target/app.jar
ARG TARGET_LIB=/app/target/libs

# Copy local code to the container image.
WORKDIR /app
COPY . .

# Build a release artifact.
RUN ls $TARGET_JAR  && ls $TARGET_LIB || mvn package -DskipTests

# Use AdoptOpenJDK for base image.
# It's important to use OpenJDK 8u191 or above that has container support enabled.
# https://hub.docker.com/r/adoptopenjdk/openjdk8
# https://docs.docker.com/develop/develop-images/multistage-build/#use-multi-stage-builds
FROM adoptopenjdk/openjdk8:jdk8u202-b08-alpine-slim
ARG TARGET_JAR=/app/target/app.jar
ARG TARGET_LIB=/app/target/libs

WORKDIR /app
ENV TZ America/Chicago
ENV PORT=8080
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom -Dserver.port=${PORT}"
ENV TARGET_MAIN=com.networknt.server.Server
COPY --from=builder $TARGET_LIB libs
COPY --from=builder $TARGET_JAR app.jar
CMD ["/bin/sh","-c","java $JAVA_OPTS -cp '/app/app.jar:/app/libs/*' $TARGET_MAIN"]