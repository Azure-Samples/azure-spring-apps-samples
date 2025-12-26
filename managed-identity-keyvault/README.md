# Access Key Vault with managed identity

This sample shows how to access Key Vault with managed identity in Azure Spring Apps.

You need include [ManagedIdentityCredentialBuilder](https://docs.microsoft.com/java/api/com.azure.identity.managedidentitycredentialbuilder?view=azure-java-stable) and [SecretClientBuilder](https://docs.microsoft.com/java/api/com.azure.security.keyvault.secrets.secretclientbuilder?view=azure-java-stable) in your code. In this sample project, you could refer to [MainController.java](https://github.com/Azure-Samples/azure-spring-apps-samples/blob/main/managed-identity-keyvault/src/main/java/com/microsoft/azure/MainController.java#L28). 

## Prerequisite

* [JDK 21](https://docs.microsoft.com/azure/java/jdk/java-jdk-install)
* [Maven 3.0 and above](http://maven.apache.org/install.html)
* [Azure CLI](https://docs.microsoft.com/cli/azure/install-azure-cli?view=azure-cli-latest) or [Azure Cloud Shell](https://docs.microsoft.com/azure/cloud-shell/overview)
* An existing Key Vault. If you need to create a Key Vault, you can use the [Azure Portal](https://docs.microsoft.com/azure/key-vault/secrets/quick-create-portal) or [Azure CLI](https://docs.microsoft.com/cli/azure/keyvault?view=azure-cli-latest#az-keyvault-create)

## How to run 

1. Run `mvn clean package` after specifying the URI of your Key Vault in [application.properties](./src/main/resources/application.properties).
1. Create an instance of Azure Spring Apps.
    ```
    az spring create -n <resource name> -g <resource group name>
    ```
1. Create an app with public domain assigned.
    ```
    az spring app create -n <app name> --service <resource name> -g <resource group name> --assign-endpoint true --runtime-version Java_21
    ```
1. Enable system-assigned managed identity for your app and take note of the principal id from the command output.
   ```
   az spring app identity assign -n <app name> --service <resource name> -g <resource group name>
   ```
1. Assign `Key Vault Secrets User` role to the system-assigned managed identity.
1. Deploy app with jar.
    ```
    az spring app deploy -n <app name> --service <resource name> -g <resource group name> --jar-path ./target/asc-managed-identity-keyvault-sample-0.1.0.jar
    ```
1.  Verify app is running. Instances should have status `RUNNING` and discoveryStatus `UP`. 
    ```
    az spring app show -n <app name> --service <resource name> -g <resource group name>
    ```
1. Verify sample is working. The url is fetched from previous step.
    ```
    # Create a secret in Key Vault
    curl -X PUT {url}/secrets/{secret-name}?value={value}

    # Get the value of secret-name you just created before
    curl {url}/secrets/{secret-name}
    ```