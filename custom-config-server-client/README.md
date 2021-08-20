# Access Azure Spring Cloud managed Config Server

This sample shows how to access Azure Spring Cloud managed Config Server through Azure RBAC when your applications are running outside Azure Spring Cloud.

## Prerequisite

* [JDK 8](https://docs.microsoft.com/en-us/azure/java/jdk/java-jdk-install)
* [Maven 3.0 and above](http://maven.apache.org/install.html)
* [Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli?view=azure-cli-latest) or [Azure Cloud Shell](https://docs.microsoft.com/en-us/azure/cloud-shell/overview)

## How to run

1. Clone this repo and go to folder

    ```bash
    git clone https://github.com/Azure-Samples/Azure-Spring-Cloud-Samples
    cd Azure-Spring-Cloud-Samples/custom-config-server-client
    ```

1. Install Azure CLI extension for Azure Spring Cloud

    ```bash
    az extension add --name spring-cloud
    ```

1. Create an instance of Azure Spring Cloud

    ```bash
    az spring-cloud create -n <service name> -g <resource group name>
    ```

1. Using piggymetrics-config as the backend git repo from which Azure Spring Cloud to pull config
   
    ```bash
    az spring-cloud config-server git set --name <service name> --uri "https://github.com/Azure-Samples/piggymetrics-config.git"
    ```
   
1. Create a service principal to access the Config Server in your application. Please mark down 
    ```bash
    az ad sp create-for-rbac --scopes /subscriptions/<subscription id>/resourceGroups/<resource group name>/providers/Microsoft.AppPlatform/Spring/<service name> --role "Azure Spring Cloud Data Reader"
    ```
   The output includes credentials you will need in the next step.
   
1. Update the `application.properties` under `src/main/resources` with the credentials you get above and your Azure Spring Cloud instance.
    ```properties
    spring.cloud.config.uri=https://<service name>.svc.asc-test.net/config
    access.token.clientId=<appId>
    access.token.secret=<password>
    access.token.tenantId=<tenant>
    ```

1. Package the app using maven and run it locally
    ```bash
    mvn clean package -D skipTests
    mvn spring-boot:run
    ```
   
1. The endpoint should be access now with the configuration content
    ```bash
   curl http://127.0.0.1:8080/config
   20000%
    ```