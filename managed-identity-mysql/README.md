# Access Azure Database for MySQL with managed identity

This sample shows how to access Azure Database for MySQL with managed identity in `Azure Spring Cloud`.

## Prerequisite

* [JDK 8](https://docs.microsoft.com/en-us/azure/java/jdk/java-jdk-install)
* [Maven 3.0 and above](http://maven.apache.org/install.html)
* [Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli?view=azure-cli-latest) or [Azure Cloud Shell](https://docs.microsoft.com/en-us/azure/cloud-shell/overview)
* An existing Key Vault. If you need to create a Key Vault, you can use the [Azure Portal](https://docs.microsoft.com/en-us/azure/key-vault/secrets/quick-create-portal) or [Azure CLI](https://docs.microsoft.com/en-us/cli/azure/keyvault?view=azure-cli-latest#az-keyvault-create)
* An existing Azure Database for MySQL instance with a database with name `demo`. If you need to create Azure Database for MySQL, you can use the [Azure Portal](https://docs.microsoft.com/en-us/azure/mysql/quickstart-create-mysql-server-database-using-azure-portal) or [Azure CLI](https://docs.microsoft.com/en-us/azure/mysql/quickstart-create-mysql-server-database-using-azure-cli)

## How to run

1. Run mvn clean package after specifying the information of your Key Vault URI and Azure Database for MySQL in application.properties.
    ```properties
    spring.datasource.url=jdbc:mysql://<mysql instance name>.mysql.database.azure.com:3306/demo?serverTimezone=UTC
    spring.datasource.username=<mysql username>@<mysql instance name>
    spring.cloud.azure.keyvault.secret.endpoint=https://<keyvault name>.vault.azure.net/
    ```
2. Install Azure CLI extension version 2.0.67 or higher for Azure Spring Cloud by running below command.
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
6. Grant permission of Key Vault to the system-assigned managed identity.
    ```
    az keyvault set-policy -n <keyvault name> -g <resource group of keyvault> --secret-permissions get set list --object-id <principal-id-you-got-in-step5>
    ```
7. Add Azure Database for MySQL password in Key Vault.
    ```
    az keyvault secret set --vault-name <keyvault name> --name MYSQL-PASSWORD --value <MySQL-PASSWORD>
    ```
8. Deploy app with jar.
    ```
    az spring-cloud app deploy -n <app name> -s <resource name> -g <resource group name> --jar-path ./target/asc-managed-identity-mysql-sample-0.1.0.jar
    ```
9.  Verify app is running. Instances should have status `RUNNING` and discoveryStatus `UP`. 
    ```
    az spring-cloud app show -n <app name> -s <resource name> -g <resource group name>
    ```
10. Verify sample is working. The url is fetched from previous step.
    ```
    # Create an entry in table
    curl --header "Content-Type: application/json" \
        --request POST \
        --data '{"description":"configuration","details":"congratulations, you have set up JDBC correctly!","done": "true"}' \
        {url}
    # List entires in table
    curl {url}
    ```