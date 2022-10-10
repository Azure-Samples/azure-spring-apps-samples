# Service registry in Azure Spring Apps

This sample shows how to use embedded service registry in `Azure Spring Apps`.

### Prerequisite

* [JDK 11](https://docs.microsoft.com/en-us/azure/java/jdk/java-jdk-install)
* [Maven 3.0 and above](http://maven.apache.org/install.html)
* [Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli?view=azure-cli-latest) or [Azure Cloud Shell](https://docs.microsoft.com/en-us/azure/cloud-shell/overview)

### Concept
Azure Spring Apps will create a dns record for each app, you can access to the app by http://{AppName}.
You can use the url to communicate with other microservices, do not need to import any other dependencies.
It also supports [blue-green deployment](https://learn.microsoft.com/en-us/azure/spring-apps/concepts-blue-green-deployment-strategies).

Azure Spring Apps will probe each instance and put the health instance into the dns record. Following [health probe](https://learn.microsoft.com/en-us/azure/spring-apps/how-to-configure-health-probes-graceful-termination) you can set appropriate configuration.


![](./media/service-registry.jpeg)

### Arch
In this sample, we will create 3 apps, gateway, greeting-client, greeting-server.

We will expose public endpoint for gateway, gateway will proxy request to greeting-client, greeting-client will call greeting-server.

All these communications are using embedded service registry.

![](./media/service-registry-arch.jpeg)

### How to run

1. Run `mvn clean package` under `embedded-service-registry`.
2. Install Azure CLI extension for Azure Spring Cloud by running below command.
```
az extension add --name spring
```
3. Create an instance of Azure Spring Cloud.
```
az spring-cloud create -n <resource name> -g <resource group name>
```
4. Create apps.
```
az spring-cloud app create -n gateway -s <resource name> -g <resource group name> --is-public true
az spring-cloud app create -n greeting-client -s <resource name> -g <resource group name> 
az spring-cloud app create -n greeting-server -s <resource name> -g <resource group name>
```
5. Deploy app with jar
```
az spring-cloud app deploy -n gateway -s <resource name> -g <resource group name> --jar-path gateway/target/gateway-0.0.1-SNAPSHOT.jar
az spring-cloud app deploy -n greeting-client -s <resource name> -g <resource group name> --jar-path greeting-server/target/greeting-server-0.0.1-SNAPSHOT.jar
az spring-cloud app deploy -n greeting-server -s <resource name> -g <resource group name> --jar-path greeting-client/target/greeting-client-0.0.1-SNAPSHOT.jar
```
6. Verify sample is working. The url is fetched from step 4.
``` 
curl {url}/greeting-client/hello

return client receive 'server says hello' from server side
```