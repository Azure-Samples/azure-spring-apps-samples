# Run Java 8 app in Azure Spring Cloud

This sample shows how to run Java 8 app in `Azure Spring Cloud`.

## Prerequisite

* [JDK 8](https://docs.microsoft.com/en-us/azure/java/jdk/java-jdk-install)
* [Maven 3.0 and above](http://maven.apache.org/install.html)
* [Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli?view=azure-cli-latest) or [Azure Cloud Shell](https://docs.microsoft.com/en-us/azure/cloud-shell/overview)

## How to run

1. Clone this repo and go to folder

    ```bash
    git clone https://github.com/Azure-Samples/Azure-Spring-Cloud-Samples
    cd Azure-Spring-Cloud-Samples/java-8-sample
    ```

1. Package the app using maven

    ```bash
    mvn clean package -D skipTests
    ```

1. Install Azure CLI extension for Azure Spring Cloud

    ```bash
    az extension add --name spring-cloud
    ```

1. Create an instance of Azure Spring Cloud

    ```bash
    az spring-cloud create -n <service name> -g <resource group name>
    ```

1. Create a public repo that only has the provided `application.yml` file

1. Using the URI of that repo, configure Azure Spring Cloud to pull config from it

    ```bash
    az spring-cloud config-server git set --name <service name> --uri <my config git repo>
    ```

1. Create an app with public domain assigned

    ```bash
    az spring-cloud app create -n <app name> -s <service name> -g <resource group name> --is-public true
    ```

1. Deploy the newly created jar

    ```bash
    az spring-cloud app deploy -n <app name> -s <service name> -g <resource group name> --jar-path ./target/java8-ASC-demo-0.0.1-SNAPSHOT.jar
    ```

1. Using the output of the new app instance, retrieve the "App Instance Name" and use in the following command to review the logs

    ```bash
    az spring-cloud app logs -f -n <app name> -i <App Instance Name>
    ```

1. Verify app is running. Instances should have status `RUNNING` and discoveryStatus `UP`

    ```bash
    az spring-cloud app show -n <app name> -s <service name> -g <resource group name>
    ```

1. Visit the Azure portal to see further information about the application.
