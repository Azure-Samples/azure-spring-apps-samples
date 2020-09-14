# Invoke Azure Function with managed identity

This sample shows how to invoke an Azure Function securely from `Azure Spring Cloud` using managed identity.

## Prerequisite

* [JDK 8](https://docs.microsoft.com/en-us/azure/java/jdk/java-jdk-install)
* [Maven 3.0 and above](http://maven.apache.org/install.html)
* [Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli?view=azure-cli-latest) or [Azure Cloud Shell](https://docs.microsoft.com/en-us/azure/cloud-shell/overview)
* An Http triggered [Azure Function](https://docs.microsoft.com/en-us/azure/azure-functions/functions-create-first-azure-function-azure-cli?pivots=programming-language-java) with [authentication enabled](https://docs.microsoft.com/en-us/azure/app-service/overview-authentication-authorization)

## How to run 

1. Run `mvn clean package` after specifying the URI and trigger name for your funciton in [application.properties](./src/main/resources/application.properties).
2. Install Azure CLI extension for Azure Spring Cloud by running below command.
    ```
    az extension add --name spring-cloud
    ```
3. Create an instance of Azure Spring Cloud.
    ```
    az spring-cloud create -n <resource name> -g <resource group name>
    ```
4. Create an app with public domain assigned.
    ```
    az spring-cloud app create -n <app name> -s <resource name> -g <resource group name> --is-public true 
    ```
5. Enable system-assigned managed identity for your app and take note of the principal id from the command output.
   ```
   az spring-cloud app identity assign -n <app name> -s <resource name> -g <resource group name>
   ```
6. Deploy app with jar.
    ```
    az spring-cloud app deploy -n <app name> -s <resource name> -g <resource group name> --jar-path ./target/asc-managed-identity-function-sample-0.1.0.jar
    ```
7.  Verify app is running. Instances should have status `RUNNING` and discoveryStatus `UP`. 
    ```
    az spring-cloud app show -n <app name> -s <resource name> -g <resource group name>
    ```
8. Verify sample is working. The url is fetched from previous step.
    ```
    # Invoke the function
    curl {url}/func/{name}
    ```