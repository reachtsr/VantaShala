FROM frolvlad/alpine-oraclejdk8:slim
RUN mkdir -p /app/
ADD VantaShala-bin.tar /app/
EXPOSE 8080
EXPOSE 27017
MAINTAINER Gopi K Kancharla
ENTRYPOINT sh /app/VantaShala-bin/bin/bootstrap

