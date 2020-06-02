# Access Key Vault with managed identity

This sample shows how to access Key Vault with managed identity in `Azure Spring Cloud`.

You need include [ManagedIdentityCredentialBuilder](https://docs.microsoft.com/en-us/java/api/com.azure.identity.managedidentitycredentialbuilder?view=azure-java-stable) and [SecretClientBuilder](https://docs.microsoft.com/en-us/java/api/com.azure.security.keyvault.secrets.secretclientbuilder?view=azure-java-stable) in your code. In this sample project, you could refer to [MainController.java](https://github.com/Azure-Samples/Azure-Spring-Cloud-Samples/blob/master/managed-identity-keyvault/src/main/java/com/microsoft/azure/MainController.java#L28). 

## Prerequisite

* [JDK 8](https://docs.microsoft.com/en-us/azure/java/jdk/java-jdk-install)
* [Maven 3.0 and above](http://maven.apache.org/install.html)
* [Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli?view=azure-cli-latest) or [Azure Cloud Shell](https://docs.microsoft.com/en-us/azure/cloud-shell/overview)
* An existing Key Vault. If you need to create a Key Vault, you can use the [Azure Portal](https://docs.microsoft.com/en-us/azure/key-vault/secrets/quick-create-portal) or [Azure CLI](https://docs.microsoft.com/en-us/cli/azure/keyvault?view=azure-cli-latest#az-keyvault-create)

## How to run 

1. Run `mvn clean package` after specifying the URI of your Key Vault in [application.properties](./src/main/resources/application.properties).
2. Install Azure CLI extension for Azure Spring Cloud by running below command.
    ```
    az extension add -y --source https://azureclitemp.blob.core.windows.net/spring-cloud/spring_cloud-0.1.0-py2.py3-none-any.whl
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
6. Grant permission of Key Vault to the system-assigned managed identity.
    ```
    az keyvault set-policy -n keyvault_name -g resource_group_of_keyvault --secret-permissions get set --object-id <principal-id-you-got-in-step5>
    ```
7. Deploy app with jar.
    ```
    az spring-cloud app deploy -n <app name> -s <resource name> -g <resource group name> --jar-path ./target/asc-managed-identity-keyvault-sample-0.1.0.jar
    ```
8.  Verify app is running. Instances should have status `RUNNING` and discoveryStatus `UP`. 
    ```
    az spring-cloud app show -n <app name> -s <resource name> -g <resource group name>
    ```
9. Verify sample is working. The url is fetched from previous step.
    ```
    # Create a secret in Key Vault
    curl -X PUT {url}/secrets/{secret-name}?value={value}

    # Get the value of secret-name you just created before
    curl {url}/secrets/{secret-name}
    ```