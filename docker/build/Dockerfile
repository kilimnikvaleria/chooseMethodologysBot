# Build
FROM openjdk:11-oracle as build
WORKDIR /root/application
COPY . .
RUN ./gradlew -Dorg.gradle.daemon=false clean build installDist

# Image
FROM openjdk:8-jre-alpine
WORKDIR /root/application
COPY --from=build /root/application/build/install /root/

EXPOSE 8080
CMD /root/chooseMethodologys/bin/chooseMethodologys
