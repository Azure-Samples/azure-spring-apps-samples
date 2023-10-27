# Access Azure Spring Apps managed Spring Cloud Service Registry

This sample shows how to access Azure Spring Apps managed Spring Cloud Service Registry through Azure RBAC when your applications are running outside Azure Spring Apps.

## Prerequisite

* [JDK 8](https://docs.microsoft.com/azure/java/jdk/java-jdk-install)
* [Maven 3.0 and above](http://maven.apache.org/install.html)
* [Azure CLI](https://docs.microsoft.com/cli/azure/install-azure-cli?view=azure-cli-latest) or [Azure Cloud Shell](https://docs.microsoft.com/azure/cloud-shell/overview)

## How to run

1. Clone this repo and go to folder

    ```bash
    git clone https://github.com/Azure-Samples/Azure-Spring-Cloud-Samples
    cd Azure-Spring-Cloud-Samples/custom-eureka-client
    ```

1. Install Azure CLI extension for Azure Spring Apps

    ```bash
    az extension add --name spring-cloud
    ```

1. Create an instance of Azure Spring Apps

    ```bash
    az spring-cloud create -n <service name> -g <resource group name>
    ```

1. Create a service principal to access the Config Server in your application.
    ```bash
    az ad sp create-for-rbac --scopes /subscriptions/<subscription id>/resourceGroups/<resource group name>/providers/Microsoft.AppPlatform/Spring/<service name> --role "Azure Spring Cloud Service Registry Contributor"
    ```
   The output includes credentials you will need in the next step.
   
1. Update the `application.properties` under `src/main/resources` with the credentials you get in the above step and your Azure Spring Apps instance.
    ```properties
    eureka.client.serviceUrl.defaultZone=https://<service name>.svc.asc-test.net/eureka/eureka
    access.token.clientId=<appId>
    access.token.secret=<password>
    access.token.tenantId=<tenant>
    ```

1. Package the app using maven and run it locally
    ```bash
    mvn clean package -D skipTests
    mvn spring-boot:run
    ```
   
1. You can query the apps which register to the eureka server by app name
    ```bash
   curl http://127.0.0.1:8080/TEST
   [{"port":8080,"host":"host.docker.internal","scheme":"http","metadata":{"management.port":"8080"},"secure":false,"instanceId":"host.docker.internal:test","serviceId":"TEST","instanceInfo":{"instanceId":"host.docker.internal:test","app":"TEST","appGroupName":null,"ipAddr":"x.x.x.x","sid":"na","homePageUrl":"http://host.docker.internal:8080/","statusPageUrl":"http://host.docker.internal:8080/actuator/info","healthCheckUrl":"http://host.docker.internal:8080/actuator/health","secureHealthCheckUrl":null,"vipAddress":"test","secureVipAddress":"test","countryId":1,"dataCenterInfo":{"@class":"com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo","name":"MyOwn"},"hostName":"host.docker.internal","status":"UP","overriddenStatus":"UNKNOWN","leaseInfo":{"renewalIntervalInSecs":30,"durationInSecs":90,"registrationTimestamp":xxxxxxx,"lastRenewalTimestamp":xxxxxxxx,"evictionTimestamp":0,"serviceUpTimestamp":xxxxxxxx},"isCoordinatingDiscoveryServer":false,"metadata":{"management.port":"8080"},"lastUpdatedTimestamp":xxxxxxxx,"lastDirtyTimestamp":xxxxxxxx,"actionType":"ADDED","asgName":null},"uri":"http://host.docker.internal:8080"}]%
    ```