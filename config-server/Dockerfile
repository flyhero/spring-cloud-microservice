FROM java:8-jre
MAINTAINER Alexander Lukyanchikov <sqshq@sqshq.com>

ADD ./target/config-server-0.0.1-SNAPSHOT.jar /dev/app.jar
CMD ["java", "-Xmx200m", "-jar", "/app/monitoring.jar"]

EXPOSE 8881