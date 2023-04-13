FROM openjdk:17-jdk-alpine
MAINTAINER <aliKalema98@gmai.com>
COPY target/shop-file-storage-0.0.1-SNAPSHOT.jar shop-file-storage-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","shop-file-storage-0.0.1-SNAPSHOT.jar"]
