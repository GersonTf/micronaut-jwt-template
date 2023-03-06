FROM gradle:jdk17-alpine AS build
COPY ../../.. /home/gradle
RUN gradle build -x check

FROM openjdk:17-oracle

WORKDIR /home/app
COPY --from=build /home/gradle/build/docker/main/layers/libs /home/app/libs
COPY --from=build /home/gradle/build/docker/main/layers/resources /home/app/resources
COPY --from=build /home/gradle/build/docker/main/layers/application.jar /home/app/application.jar

ENV FIRE_JWT_SECRET=$FIRE_JWT_SECRET
ENV MONGO_URI=$MONGO_URI

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/home/app/application.jar"]
