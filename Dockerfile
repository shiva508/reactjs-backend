# Used to analyze dependencies bacause jdeps from JDK 17 has a bug
FROM amazoncorretto:22-alpine as deps

# Identify dependencies
COPY ./target/reactjs-backend-0.0.1-SNAPSHOT.jar /dear-comrade/reactjs-backend-0.0.1-SNAPSHOT.jar
RUN mkdir /dear-comrade/unpacked && \
    cd /dear-comrade/unpacked && \
    unzip ../reactjs-backend-0.0.1-SNAPSHOT.jar && \
    cd .. && \
    $JAVA_HOME/bin/jdeps \
    --ignore-missing-deps \
    --print-module-deps \
    -q \
    --recursive \
    --multi-release 22 \
    --class-path="./unpacked/BOOT-INF/lib/*" \
    --module-path="./unpacked/BOOT-INF/lib/*" \
    ./reactjs-backend-0.0.1-SNAPSHOT.jar > /deps.info

# base image to build a JRE
FROM amazoncorretto:22-alpine as corretto-jdk

# required for strip-debug to work
RUN apk add --no-cache binutils

# copy module dependencies info
COPY --from=deps /deps.info /deps.info

# Build small JRE image
RUN $JAVA_HOME/bin/jlink \
    --verbose \
    --add-modules $(cat /deps.info) \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /customjre

# main app image
FROM alpine:latest
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# copy JRE from the base image
COPY --from=corretto-jdk /customjre $JAVA_HOME

# Add app user
ARG APPLICATION_USER=appuser
RUN adduser --no-create-home -u 1000 -D $APPLICATION_USER

# Configure working directory
RUN mkdir /dear-comrade && \
    chown -R $APPLICATION_USER /dear-comrade

USER 1000

COPY --chown=1000:1000 ./target/reactjs-backend-0.0.1-SNAPSHOT.jar /dear-comrade/reactjs-backend-0.0.1-SNAPSHOT.jar
WORKDIR /dear-comrade

EXPOSE 8080
ENTRYPOINT [ "/jre/bin/java", "-jar", "/dear-comrade/reactjs-backend-0.0.1-SNAPSHOT.jar" ]