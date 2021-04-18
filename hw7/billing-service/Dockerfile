FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine

ENV JAVA_OPTS ""

COPY build/libs/*.jar /app/

WORKDIR /app

ENTRYPOINT java $JAVA_OPTS -jar *.jar