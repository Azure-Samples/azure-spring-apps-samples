# Football Job

## About the sample
This sample project is derived from Spring Batch sample [Football Job](https://raw.githubusercontent.com/spring-projects/spring-batch/main/spring-batch-samples/src/main/java/org/springframework/batch/samples/football/README.md). It is a statistics loading job. Instead of triggering by unit test in original sample, it is initiated by the main method of FootballJobApplication. 

Please reference [README.md](https://raw.githubusercontent.com/spring-projects/spring-batch/main/spring-batch-samples/src/main/java/org/springframework/batch/samples/football/README.md) to learn more.


## How to run

Please ensure you have an Azure Spring Apps service instance in Enterprise plan ready for use. Set the name of resource group and service instance to variables `RESOURCE_GROUP` and `SPRING_APPS_SERVICE`. All steps are executed under the root folder of current Java sample project.

* Clear out the existing classes from your sample project.
```
mvn clean
```

* Create the job.
```
az spring job create -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} --name football
```

* Deploy this football sample project to job. It uploads and compiles source code on Azure and makes it ready for start. 
```
az spring job deploy -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} --name football --source-path . --build-env BP_JVM_VERSION=17.*
```

* Start job and set execution name to variable `EXECUTION_NAME`.
```
export EXECUTION_NAME = $(az spring job start -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} --name football --query name -o tsv)
```

* Query execution result according to job name and its execution name.
```
az spring job execution show -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} --job-name football --job-execution-name ${EXECUTION_NAME}
```