#Build
docker build -t api-1.0.0-snapshot .
#check if the Docker image was built successfully
docker images | grep api-1.0.0-snapshot
#Run Docker container using the image built
docker run -d --name api-1.0.0-snapshot -p 8080:8080 api-1.0.0-snapshot:latest
#check if the container is built successfully
docker ps -a | grep api-1.0.0-snapshot
#check logs
docker logs -f api-1.0.0-snapshot