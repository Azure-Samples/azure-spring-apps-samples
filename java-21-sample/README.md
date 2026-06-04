# Overview

This sample demonstrates running a **Java 21** Spring Boot application on Azure Spring Apps, highlighting the following key features:

- **Java 21 LTS** — uses the latest LTS release with `maven-compiler-source/target` set to `21`
- **Virtual Threads** — `spring.threads.virtual.enabled=true` enables Project Loom virtual threads for improved throughput under high concurrency
- **Records** — `HelloController` uses a `record` type (`Greeting`) as a lightweight, immutable data carrier
- **Switch Expressions & Text Blocks** — `/java-version` endpoint demonstrates pattern-matching switch and multi-line text blocks
- **SSL Health Indicator** — Spring Boot 3.4 `SslHealthIndicator` is enabled via an SSL bundle; certificate state is observable at `GET /actuator/health/ssl`
- **Custom SSL info endpoint** — `GET /ssl/info` renders a human-readable HTML table of certificate metadata (alias, subject, issuer, validity dates, serial number)
- **Spring Boot 3.4 + Spring Cloud 2024** — latest dependency stack with `spring-boot-starter-actuator`, Eureka client, and Config client

## Key Endpoints

| Endpoint | Description |
|---|---|
| `GET /` | Greeting page showing app name and current timestamp |
| `GET /java-version` | Displays the running JVM version and LTS tier |
| `GET /eureka` | Lists all registered Eureka applications and their instance details |
| `GET /ssl/info` | Certificate metadata for the configured SSL bundle |
| `GET /actuator/health` | Full health status including `ssl` health group |
| `GET /actuator/health/ssl` | SSL-specific health — `UP` / `WARNING` (expiring soon) / `DOWN` |
| `POST /memory/add` | Allocates 32 MB heap chunks (for memory pressure testing) |

---

# Local Development

```shell
docker run --rm -p 8761:8761 springcloud/demo-eureka-server
```

# Prepare Variables
```shell
SUBSCRIPTION=
LOCATION=
RESOURCE_GROUP=
SERVICE_NAME=

az account set --subscription $SUBSCRIPTION
```

# Standard Plan

## Deploy Application
```shell
mvn clean package
az spring app create -n java21sample --service $SERVICE_NAME -g $RESOURCE_GROUP --runtime-version Java_21 --assign-endpoint true 
az spring app deploy -n java21sample --service $SERVICE_NAME -g $RESOURCE_GROUP --artifact-path ./target/java-21-sample-1.0-SNAPSHOT.jar
```

# Enterprise Plan

## Deploy Application
```
mvn clean 
az spring app create -n java21sample --service $SERVICE_NAME -g $RESOURCE_GROUP --assign-endpoint true 
az spring app deploy -n java21sample --service $SERVICE_NAME -g $RESOURCE_GROUP --source-path . --build-env BP_JVM_VERSION=21.*
```
