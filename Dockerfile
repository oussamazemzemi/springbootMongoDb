# syntax=docker/dockerfile:1
#LABEL Maintainer=oussama.zemzemi@gmail.com
FROM openjdk:8-jdk

### COPY Files
ADD /api/target/api-1.0.0-SNAPSHOT.jar api-1.0.0-snapshot.jar

#### Launch command.
CMD ["java","-jar","api-1.0.0-snapshot.jar", "--spring.data.mongodb.host=mongo_db"]