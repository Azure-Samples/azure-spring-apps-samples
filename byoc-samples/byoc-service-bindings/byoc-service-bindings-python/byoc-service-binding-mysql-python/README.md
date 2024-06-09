### Service Binding Sample

This sample shows how to run Python apps with Azure Database for MySQL in `Azure Spring Cloud` with your custom container.

### Prerequisite

* Python & [Python extension for VS Code](https://marketplace.visualstudio.com/items?itemName=ms-python.python)
* [Docker](https://www.docker.com/products/container-runtime)
* [Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli?view=azure-cli-latest) or [Azure Cloud Shell](https://docs.microsoft.com/en-us/azure/cloud-shell/overview)

### How to run 

1. Install Azure CLI extension for Azure Spring Cloud by running below command.
    ```
    az extension add -y --source https://azureclitemp.blob.core.windows.net/spring-cloud/spring_cloud-0.1.0-py2.py3-none-any.whl
    ```
1. Create an instance of Azure Spring Cloud.
    ```
    az spring-cloud create -n <resource name> -g <resource group name>
    ```
1. Create an app with public domain assigned.
    ```
    az spring-cloud app create -n <app name> -s <resource name> -g <resource group name> --is-public true 
    ```
1. Deploy the app written in Python with your custom container.
    
1. Add a binding for this app
    ```
    az spring-cloud app binding mysql add --app <app name> -s <resource name> -g <resource group name> -n mysql --resource-id <resource id of your mysql> --username <mysql username> --key <mysql password> --database-name <database name>
    ```
1. Restart the app
    ```
    az spring-cloud app restart -n <app name> -s <resource name> -g <resource group name>
    ```
1. Verify app is running. Instances should have status `RUNNING` and discoveryStatus `UP`. 
    ```
    az spring-cloud app show -n <app name> -s <resource name> -g <resource group name>
    ```
