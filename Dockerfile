FROM gradle:8.5-jdk17 AS booking-service-build

COPY booking-service /home/gradle/booking-service

RUN <<EOT bash
cd /home/gradle/booking-service
gradle clean build
EOT


FROM gradle:8.5-jdk17 AS payment-service-build

COPY payment-service /home/gradle/payment-service

RUN <<EOT bash
cd /home/gradle/payment-service
gradle clean build
EOT

FROM openjdk:17.0.1-jdk-slim AS booking-service-app

RUN adduser --system --group app-user

COPY --from=booking-service-build --chown=app-user:app-user /home/gradle/booking-service/build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8080
USER app-user

CMD ["java", "-jar", "app.jar"]

FROM openjdk:17.0.1-jdk-slim AS payment-service-app

RUN adduser --system --group app-user

COPY --from=payment-service-build --chown=app-user:app-user /home/gradle/payment-service/build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8081
USER app-user

CMD ["java", "-jar", "app.jar"]