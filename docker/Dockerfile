FROM openjdk:17-alpine
ENV TZ=Asia/Shanghai
ENV LOG_HOME=/data/applogs
ADD home-education-tools-0.0.1-SNAPSHOT.jar /data/application/
WORKDIR /data/application/
EXPOSE 8080
EXPOSE 5025
ENTRYPOINT ["java","-jar","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5025", "home-education-tools-0.0.1-SNAPSHOT.jar"]