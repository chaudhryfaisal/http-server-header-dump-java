FROM openjdk:8-jre-alpine
ARG APP_JAR="target/http-server-header-dump-java-0.0.1-SNAPSHOT.jar"

EXPOSE  8080 8443

ENV JAVA_OPTS "-Djava.security.egd=file:/dev/./urandom"
WORKDIR /app

ADD $APP_JAR app.jar

ENTRYPOINT exec java $JAVA_OPTS -jar /app/app.jar