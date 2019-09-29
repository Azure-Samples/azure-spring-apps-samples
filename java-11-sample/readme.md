### Java 11 Sample

This sample shows how to run Java 11 app in Azure Spring Cloud Service.

### How to run 

1. Run `mvn clean package` under `java-11-sample`.
2. Install CLI for Azure Spring Cloud.
3. Create an instance of Azure Spring Cloud.
```
az spring-cloud create -n <resource name> -g <resource group name>
```
4. Create an app with public domain assigned.
```
az spring-cloud app create -n <app name> -s <resource name> -g <resource group name> --is-public true 
```
5. Deploy app with jar
```
az spring-cloud app deploy -n <app name> -s <resource name> -g <resource group name> --jar-path ./target/hello-world-11-1.0-SNAPSHOT.jar
```
6. Verify app is running. Instances should have status `RUNNING` and discoveryStatus `UP`. 
```
az spring-cloud app show -n <app name> -s <resource name> -g <resource group name>
```
7. Verify sample is working. The url is fetch from previous step. 
```
curl {url}/{name}
Hello {name}
```
