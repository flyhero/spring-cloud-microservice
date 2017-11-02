FROM java:8
VOLUME /tmp
ADD ./target/config-server-0.0.1-SNAPSHOT.jar config-server.jar
RUN sh -c 'touch /config-server.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /config-server.jar" ]

EXPOSE 8881