# Running Locally

docker run --publish 8888:8888 steeltoeoss/config-server:latest --spring.cloud.config.server.git.uri=https://github.com/Azure-Samples/Azure-Spring-Cloud-Samples --spring.cloud.config.server.git.search-paths=steeltoe-sample/config

docker run --publish 8761:8761 steeltoeoss/eureka-server
