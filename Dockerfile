FROM openjdk:8-jre-alpine
ARG APP_JAR="target/app.jar"

EXPOSE  8080 8443

ENV TZ America/Chicago
ENV JAVA_OPTS "-Djava.security.egd=file:/dev/./urandom"
ENV APP_ARGS ""
WORKDIR /app

ADD target/libs /app/libs
ADD $APP_JAR /app/app.jar

CMD /bin/sh -c "java -cp '/app/app.jar:/app/libs/*' $JAVA_OPTS com.networknt.server.Server $APP_ARGS"
