FROM openjdk:11.0.7-slim
WORKDIR /
ADD build/libs/app-0.0.1.jar .

CMD java \
    -Dspring.r2dbc.url=r2dbc:postgresql://db:5432/postgres \
    -jar app-0.0.1.jar