FROM java:8
VOLUME /tmp
ADD ./target/gateway-service-0.0.1-SNAPSHOT.jar gateway-service.jar
RUN sh -c 'touch /gateway-service.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /gateway-service.jar" ]
