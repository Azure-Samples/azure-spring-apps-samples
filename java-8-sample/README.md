# Run Java 8 app in Azure Spring Apps

This sample shows how to run Java 8 app in Azure Spring Apps.

## Prerequisite

* [JDK 8](https://docs.microsoft.com/azure/java/jdk/java-jdk-install)
* [Maven 3.0 and above](http://maven.apache.org/install.html)
* [Azure CLI](https://docs.microsoft.com/cli/azure/install-azure-cli?view=azure-cli-latest) or [Azure Cloud Shell](https://docs.microsoft.com/azure/cloud-shell/overview)

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

1. Install Azure CLI extension for Azure Spring Apps

   ```bash
    az extension add --name spring
    ```

1. Create an instance of Azure Spring Apps

    ```bash
    az spring create -n <service name> -g <resource group name>
    ```

1. Create a public repo that only has the provided `application.yml` file

1. Using the URI of that repo, configure Azure Spring Apps to pull config from it

    ```bash
    az spring config-server git set --name <service name> -g <resource group name> --uri <my config git repo>
    ```

1. Create an app with public domain assigned

    ```bash
    az spring app create -n <app name> -s <service name> -g <resource group name> --runtime-version Java_8 --is-public true
    ```

1. Deploy the newly created jar

    ```bash
    az spring app deploy -n <app name> -s <service name> -g <resource group name> --artifact-path ./target/java8-ASC-demo-0.0.1-SNAPSHOT.jar
    ```

1. Using the output of the new app instance, retrieve the "App Instance Name" and use in the following command to review the logs

    ```bash
    az spring app logs -f -n <app name> -s <service name> -g <resource group name>
    ```

1. Verify app is running. Instances should have status `RUNNING` and discoveryStatus `UP`

    ```bash
    az spring app show -n <app name> -s <service name> -g <resource group name>
    ```

1. Visit the Azure portal to see further information about the application.
