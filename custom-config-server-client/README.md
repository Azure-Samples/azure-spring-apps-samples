# Access Azure Spring Apps managed Config Server

> [!NOTE]
> This sample project applies to Azure Spring Apps standard pricing plan.

As Config Server is a protected resource in Azure Spring Apps service, the client from outside needs to [customize the request](https://docs.spring.io/spring-cloud-config/docs/current/reference/html/#custom-rest-template) when using Config Data or Bootstrap. Since Spring Boot 2.4, the latter approach has been deprecated. This sample shows how to custom RestTemplate using Config Data and send Bearer token in the `Authorization` header to access Config Server.

## Prerequisite

* [JDK 17](https://docs.microsoft.com/azure/java/jdk/java-jdk-install)
* [Maven 3.0 and above](http://maven.apache.org/install.html)
* [Azure CLI](https://docs.microsoft.com/cli/azure/install-azure-cli) or [Azure Cloud Shell](https://docs.microsoft.com/azure/cloud-shell/overview)

## Prepare your sample applications

Clone the sample repository to your develop environment. 
```bash
git clone https://github.com/Azure-Samples/azure-spring-apps-samples
cd azure-spring-apps-samples/custom-config-server-client
```

## Provision your Azure Spring Apps instance
Please reference doc to provision Azure Spring Apps instance: https://learn.microsoft.com/azure/spring-apps/quickstart?pivots=sc-standard

Create environment variables file `setup-env-variables.sh` based on template. 
```bash
cp setup-env-variables-template.sh setup-env-variables.sh
```

Update below resource information in `setup-env-variables.sh`.
```bash
export SUBSCRIPTION='subscription-id'                 # replace it with your subscription-id
export RESOURCE_GROUP='resource-group-name'           # existing resource group or one that will be created in next steps
export SPRING_APPS_SERVICE='azure-spring-apps-name'   # name of the service that will be created in the next steps
```

Source setting.
```bash
source ./setup-env-variables.sh
```

Update default subscription.
```bash
az account set --subscription ${SUBSCRIPTION}
```

## Create and Configure Azure Spring Service instance in Standard Pricing Plan
```bash
# Install Azure CLI extension for Azure Spring Apps
az extension add --name spring

# Create an instance of Azure Spring Apps
az spring create -g ${RESOURCE_GROUP} -n ${SPRING_APPS_SERVICE}

# Using piggymetrics-config as the backend git repo from which Azure Spring Apps to pull config
az spring config-server git set -g ${RESOURCE_GROUP} -n ${SPRING_APPS_SERVICE} --uri "https://github.com/Azure-Samples/piggymetrics-config.git"
```

## Create Microsoft Entra Service Principal for Sample App

1. Create a service principal to access the Config Server in your application. Please mark down 
  ```bash
  # Get resource id of service instance
  RESOURCE_ID=$(az spring show -g ${RESOURCE_GROUP} -n ${SPRING_APPS_SERVICE} --query id -o tsv)

  # Create service principal and create role assignment
  az ad sp create-for-rbac --scopes ${RESOURCE_ID} --role "Azure Spring Cloud Config Server Reader"
  ```
  
  The output includes credentials you will need in the next step.
   
1. Update the `application.properties` under `src/main/resources` with the credentials you get above and your Azure Spring Apps instance.
    ```properties
    spring.config.import=configserver:https://<service name>.svc.asc-test.net/config
    access.token.clientId=<appId>
    access.token.secret=<password>
    access.token.tenantId=<tenant>
    ```

## Execute Sample App
```bash
mvn clean package -D skipTests
mvn spring-boot:run

# Get output of config and check its value
curl http://127.0.0.1:8080/config
```