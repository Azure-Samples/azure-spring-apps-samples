# Use Circuit Breaker Dashboard with Azure Spring Apps
[Spring Cloud Netflix Turbine](https://github.com/Netflix/Turbine) is widely used to aggregates multiple [Hystrix](https://github.com/Netflix/Hystrix) metrics streams so that it can be monitored in a single view using Hystrix dashboard. In this tutorial, we will demonstrate how to use them on Azure Spring Apps.

>[!NOTE]
> Netflix Hystrix is widely used in many existing Spring Cloud apps but it is no longer in active development. If you are developing new project, you should use Spring Cloud Circuit Breaker implementations like [resilience4j](https://github.com/resilience4j/resilience4j) instead. Different from Turbine shown in this tutorial, the new Spring Cloud Circuit Breaker framework unifies all implementations of its metrics data pipeline into Micrometer. We are still working on supporting micrometer in Azure Spring Apps, thus it will not be covered by this tutorial.

## Prepare your sample applications

Clone the sample repository to your develop environment. It is forked from [this repository](https://github.com/StackAbuse/spring-cloud/tree/master/spring-turbine), check out the original [blog](https://stackabuse.com/spring-cloud-turbine/) if you want to learn more.

```bash
git clone https://github.com/Azure-Samples/Azure-Spring-Cloud-Samples.git
cd Azure-Spring-Cloud-Samples/hystrix-turbine-sample
```

Build the 3 applications that will be used in this tutorial.
* user-service: A simple REST service that has a single endpoint of `/personalized/{id}`
* recommendation-service: A simple REST service that has a single endpoint of `/recommendations`, which will be called by user-service.
* hystrix-turbine: A Hystrix dashboard service to display Hystrix streams and a Turbine service aggregating Hystrix metrics stream from other services.
  
```bash
mvn clean package -D skipTests -f user-service/pom.xml
mvn clean package -D skipTests -f recommendation-service/pom.xml
mvn clean package -D skipTests -f hystrix-turbine/pom.xml
```

## Provision your Azure Spring Apps instance

Please reference doc to provision Azure Spring Apps instance: https://learn.microsoft.com/azure/spring-apps/quickstart?pivots=sc-standard

Create environment variables file `setup-env-variables.sh` based on template. 
```bash
cp .\setup-env-variables-template.sh setup-env-variables.sh
```

Update below resource information in `setup-env-variables.sh`.
```bash
export SUBSCRIPTION='subscription-id'                 # replace it with your subscription-id
export RESOURCE_GROUP='resource-group-name'           # existing resource group or one that will be created in next steps
export SPRING_APPS_SERVICE='azure-spring-apps-name'   # name of the service that will be created in the next steps
```

Source setting.
```bash
source ./setup-env-variables.sh
```

Update default subscription.
```bash
az account set --subscription ${SUBSCRIPTION}
```

## Deploy your applications to Azure Spring Apps

Be aware that our apps are not using Config Server, thus no need to config Config Server for Azure Spring Apps before deployment.

```bash
az spring app create -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} -n user-service --is-public
az spring app create -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} -n recommendation-service
az spring app create -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} -n hystrix-turbine --is-public

az spring app deploy -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} -n user-service --artifact-path user-service/target/user-service.jar
az spring app deploy -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} -n recommendation-service --artifact-path recommendation-service/target/recommendation-service.jar
az spring app deploy -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} -n hystrix-turbine --artifact-path hystrix-turbine/target/hystrix-turbine.jar
```

## Verify your apps
Get endpoint of app `user-service` and append test path.
```bash
echo $(az spring app show -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} -n user-service --query "properties.url" -o tsv)"/personalized/1"
```

Then you access `user-service` with the test path above via your browser. You should get the following output if `user-service` can access `recommendation-service`. Otherwise please try refreshing the web page again later.

```json
[{"name":"Product1","description":"Description1","detailsLink":"link1"},{"name":"Product2","description":"Description2","detailsLink":"link3"},{"name":"Product3","description":"Description3","detailsLink":"link3"}]
```

## Access your Hystrix dashboard and metrics stream

### Using public endpoints
Get the endpoint of app `hystrix-turbine` and append app path.
```bash
echo $(az spring app show -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} -n hystrix-turbine --query "properties.url" -o tsv)/hystrix
```

Then you access `hystrix-turbine` through the path above in your browser. It shows the Hystrix dashboard running in this app.

Get Turbine stream URL below. Paste the URL and click Monitor Stream, and you will see the dashboard working. If you are not viewing anything, just hit the `user-service` endpoints to generate the streams.
```bash
echo $(az spring app show -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} -n hystrix-turbine --query "properties.url" -o tsv)/turbine.stream?cluster=default
```

You can now play with your Circuit Breaker Dashboard.

>[!NOTE] 
> Be aware that in production, the Hystrix dashboard and metrics stream should not be exposed to internet. 

### Using private test endpoints

Hystrix metrics streams are also accessible from test-endpoint. As a backend service, `recommendation-service` doesn't need a public end-point. You can get endpoint using command below.
```bash
echo $(az spring test-endpoint list -g ${RESOURCE_GROUP} -n ${SPRING_APPS_SERVICE} --app recommendation-service --query "primaryTestEndpoint" -o tsv)
```

If you need to show its metrics, you need to get full path using command below.
```bash
echo $(az spring test-endpoint list -g ${RESOURCE_GROUP} -n ${SPRING_APPS_SERVICE} --app recommendation-service --query "primaryTestEndpoint" -o tsv)/actuator/hystrix.stream
```

As a web app, Hystrix dashboard should also be working on test endpoint. However, it is not working properly for two reasons: First, using test endpoint will change the base URL from `/` to `/<APP-NAME>/<DEPLOYMENT-NAME>`. Second, the web app is using absolute path for static resource. To get it worked on test endpoint, you might need to manually edit the `<base>`in the front-end files.
