./gradlew clean
./gradlew build -x test
docker-compose -f docker-compose-scaled.yaml up -d