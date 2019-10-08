# Temporary and persistent storage in Azure Spring Cloud

This sample shows how to use temporary and persistent storage in `Azure Spring Cloud`.

### Prerequisite

* [JDK 8](https://docs.microsoft.com/en-us/azure/java/jdk/java-jdk-install)
* [Maven 3.0 and above](http://maven.apache.org/install.html)
* [Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli?view=azure-cli-latest) or [Azure Cloud Shell](https://docs.microsoft.com/en-us/azure/cloud-shell/overview)

### How to run 

1. Run `mvn clean package` under `storage-sample`.
2. Install Azure CLI extension for Azure Spring Cloud by running below command.
```
az extension add -y --source https://azureclitemp.blob.core.windows.net/spring-cloud/spring_cloud-0.1.0-py2.py3-none-any.whl
```
3. Create an instance of Azure Spring Cloud.
```
az spring-cloud create -n <resource name> -g <resource group name>
```
4. Create an app with public domain assigned and persistent storage enabled. 
```
az spring-cloud app create -n <app name> -s <resource name> -g <resource group name> --is-public true --enable-persistent-storage true
```
5. Deploy app with jar
```
az spring-cloud app deploy -n <app name> -s <resource name> -g <resource group name> --jar-path ./target/storage-sample-1.0-SNAPSHOT.jar
```
6. Verify app is running. Instances should have status `RUNNING` and discoveryStatus `UP`. 
```
az spring-cloud app show -n <app name> -s <resource name> -g <resource group name>
```
7. Verify sample is working. The url is fetched from previous step. 
```
# Write content of flie-name under temporary storage
curl -X PUT {url}/tmp/{file-name}?content={content}

# Get content of file-name 
curl {url}/tmp/{file-name}
# return content you wrote before

# Write content of flie-name under persistent storage
curl -X PUT {url}/persistent/{file-name}?content={content}

# Get content of file-name 
curl {url}/persistent/{file-name}
# return content you wrote before

```
