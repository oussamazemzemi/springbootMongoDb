# syntax=docker/dockerfile:1
#LABEL ZEMZEMI OUSSAMA <oussama.zemzemi@gmail.com>
FROM openjdk:8-jdk

## MAVEN INSTALL

ARG MAVEN_VERSION=3.8.5
ARG USER_HOME_DIR="/root"
ARG SHA=89ab8ece99292476447ef6a6800d9842bbb60787b9b8a45c103aa61d2f205a971d8c3ddfb8b03e514455b4173602bd015e82958c0b3ddc1728a57126f773c743
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  && echo "${SHA}  /tmp/apache-maven.tar.gz" | sha512sum -c - \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

#COPY mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh
COPY settings-docker.xml /usr/share/maven/ref/

#ENTRYPOINT ["/usr/local/bin/mvn-entrypoint.sh"]
CMD ["mvn"]

WORKDIR /ROOT/springbootMongoDb/

# COPY Files
COPY pom.xml ./
COPY src ./src

COPY models/src ./models/src
COPY repositories/src ./repositories/src
COPY services/src ./services/src
COPY api/src ./api/src

#RUN ./mvnw dependency:go-offline

ADD /api/target/api-1.0.0-SNAPSHOT.jar api-1.0.0-snapshot.jar
EXPOSE 8080
#CMD java -jar api-1.0.0-snapshot.jar

# Define default command.
#CMD ["mvn", "--version"]
CMD ["java","-jar","api-1.0.0-snapshot.jar"]