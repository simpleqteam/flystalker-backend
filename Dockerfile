FROM openjdk:8

EXPOSE 8080/tcp

COPY build/libs/flystalkebackend*.jar flystalkerbackend.jar

ENTRYPOINT [ "java", "-jar", "/flystalkerbackend.jar" ]
