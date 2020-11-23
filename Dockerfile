FROM openjdk:11.0.7-slim
WORKDIR /
ADD build/libs/app-0.0.1.jar .

CMD java \
    $JAVA_OPTS \
    -jar app-0.0.1.jar