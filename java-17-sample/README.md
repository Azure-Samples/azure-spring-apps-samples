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
## Provision ASA Service Instance
```shell
az spring create --resource-group $RESOURCE_GROUP --name $SERVICE_NAME --sku standard --location $LOCATION
az spring app create -n java17sample --service $SERVICE_NAME -g $RESOURCE_GROUP --runtime-version Java_17 --assign-endpoint true
```

## Deploy Application
```shell
mvn clean package
az spring app deploy -n java17sample --service $SERVICE_NAME -g $RESOURCE_GROUP --artifact-path ./target/java-17-sample-1.0-SNAPSHOT.jar
```

