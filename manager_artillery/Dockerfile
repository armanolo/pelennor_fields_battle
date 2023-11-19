FROM openjdk:17

# The port your service will listen on
EXPOSE 8080

# Copy the service JAR
COPY ./build/libs/minis_tirith_artillery-0.0.1-SNAPSHOT.jar /minis_tirith_artillery.jar

# The command to run
ENTRYPOINT exec java ${JAVA_OPTS} -jar /minis_tirith_artillery.jar