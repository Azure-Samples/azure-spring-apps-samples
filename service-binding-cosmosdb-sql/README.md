# Bind your app with Azure COSMOS SQL API

This sample shows how to run app with Azure Cosmos DB(SQL) in Azure Spring Apps.

### Prerequisite

* [JDK 8](https://docs.microsoft.com/azure/java/jdk/java-jdk-install)
* [Maven 3.0 and above](http://maven.apache.org/install.html)
* [Azure CLI](https://docs.microsoft.com/cli/azure/install-azure-cli?view=azure-cli-latest) or [Azure Cloud Shell](https://docs.microsoft.com/azure/cloud-shell/overview)

### How to run 

1. Run `mvn clean package`.
1. Install Azure CLI extension for Azure Spring Apps by running below command.
    ```
    az extension add -y --source https://azureclitemp.blob.core.windows.net/spring-cloud/spring_cloud-0.1.0-py2.py3-none-any.whl
    ```
1. Create an instance of Azure Spring Apps.
    ```
    az spring-cloud create -n <resource name> -g <resource group name>
    ```
1. Create an app with public domain assigned.
    ```
    az spring-cloud app create -n <app name> -s <resource name> -g <resource group name> --is-public true 
    ```
1. Deploy app with jar
    ```
    az spring-cloud app deploy -n <app name> -s <resource name> -g <resource group name> --jar-path ./target/asc-service-binding-cosmosdb-sql-sample-0.1.0.jar
    ```
1. Add a binding for this app
    ```
    az spring-cloud app binding cosmos add --api-type sql --app <app name> -s <resource name> -g <resource group name> -n cosmos --resource-id <resource id of your cosmos db account> --database-name <database name>
    ```
1. Restart the app
    ```
    az spring-cloud app restart -n <app name> -s <resource name> -g <resource group name>
    ```
1. Verify app is running. Instances should have status `RUNNING` and discoveryStatus `UP`. 
    ```
    az spring-cloud app show -n <app name> -s <resource name> -g <resource group name>
    ```
