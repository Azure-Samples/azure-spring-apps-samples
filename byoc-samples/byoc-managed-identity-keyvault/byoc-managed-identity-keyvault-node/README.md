# Access Key Vault with managed identity in a Node.js app container

This sample shows how to access Key Vault with managed identity in `Azure Spring Cloud`'s apps written in Node.js and deployed with custom container.

You need to import [ManagedIdentityCredential](https://docs.microsoft.com/en-us/javascript/api/@azure/identity/managedidentitycredential?view=azure-node-latest) and [SecretClient](https://docs.microsoft.com/en-us/javascript/api/@azure/keyvault-secrets/secretclient?view=azure-node-latest) in your code. In this sample project, you could refer to [identity.js](identity.js).

## Prerequisite

* [Docker](https://www.docker.com/products/container-runtime)
* [Nodejs](https://nodejs.org/en/)
* [Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli?view=azure-cli-latest) or [Azure Cloud Shell](https://docs.microsoft.com/en-us/azure/cloud-shell/overview)
* An existing Key Vault. If you need to create a Key Vault, you can use the [Azure Portal](https://docs.microsoft.com/en-us/azure/key-vault/secrets/quick-create-portal) or [Azure CLI](https://docs.microsoft.com/en-us/cli/azure/keyvault?view=azure-cli-latest#az-keyvault-create)

## How to run

1. Specify the URI of your Key Vault in [Dockerfile](Dockerfile).
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
7. Deploy the app written in Node.js with your custom container.

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