FROM eclipse-temurin:17.0.5_8-jdk-alpine
ENV TZ=Asia/Shanghai
ENV LOG_HOME=/data/applogs
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories
RUN apk add --no-cache python3 python3-dev py-pip
COPY requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt -i https://pypi.tuna.tsinghua.edu.cn/simple
ADD home-education-tools-0.0.1-SNAPSHOT.jar /data/application/
WORKDIR /data/application/
RUN mkdir python
RUN mkdir excel
COPY python/ ./python/
EXPOSE 8080
EXPOSE 5025
ENTRYPOINT ["java","-jar","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5025", "home-education-tools-0.0.1-SNAPSHOT.jar"]