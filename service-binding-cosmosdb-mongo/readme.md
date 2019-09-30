### Service Binding Sample

This sample shows how to run app with Azure Cosmos DB(Mongo) in `Azure Spring Cloud`.

### Prerequisite

* Java 8
* Maven
* Azure CLI

### How to run 

1. Run `mvn clean package`.
1. Install CLI for Azure Spring Cloud.
1. Create an instance of Azure Spring Cloud.
    ```
    az spring-cloud create -n <resource name> -g <resource group name>
    ```
1. Create an app with public domain assigned.
    ```
    az spring-cloud app create -n <app name> -s <resource name> -g <resource group name> --is-public true 
    ```
1. Deploy app with jar
    ```
    az spring-cloud app deploy -n <app name> -s <resource name> -g <resource group name> --jar-path ./target/service-binding-cosmosdb-sql-0.1.0.jar
    ```
1. Add a binding for this app
    ```
    az spring-cloud app binding cosmos add --api-type mongo --app <app name> -s <resource name> -g <resource group name> -n cosmos --resource-id <resource id of your cosmosdb account> --database-name <database name>
    ```
1. Restart the app
    ```
    az spring-cloud app restart -n <app name> -s <resource name> -g <resource group name>
    ```
1. Verify app is running. Instances should have status `RUNNING` and discoveryStatus `UP`. 
    ```
    az spring-cloud app show -n <app name> -s <resource name> -g <resource group name>
    ```
