FROM amazoncorretto:17.0.7-alpine3.14 AS builder
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN ./gradlew clean build -x test

FROM amazoncorretto:17.0.7-alpine3.14
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=${PROFILES}"]
EXPOSE 8080
ENV PROFILES=default
HEALTHCHECK --interval=30s --timeout=30s --start-period=5s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider localhost:8080/actuator/health || exit 1
COPY --from=builder /app/build/libs/*SNAPSHOT.jar /app.jar
