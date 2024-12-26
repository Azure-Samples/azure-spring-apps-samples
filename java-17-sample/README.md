# Prepare Variables
```shell
SUBSCRIPTION=6c933f90-8115-4392-90f2-7077c9fa5dbd
LOCATION=westus2
RESOURCE_GROUP=dixue-asas-vnet-prod
SERVICE_NAME=dixue-asas-vnet-prod

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
# Enterprise Plan
## Provision ASA Service Instance

## Deploy Application
