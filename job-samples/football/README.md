# Football Job

## About the sample
This sample project is derived from Spring Batch sample [Football Job](https://raw.githubusercontent.com/spring-projects/spring-batch/main/spring-batch-samples/src/main/java/org/springframework/batch/samples/football/README.md). It is a statistics loading job. Instead of triggering by unit test in original sample, it is initiated by the main method of FootballJobApplication. 

Please reference [README.md](https://github.com/spring-projects/spring-batch/blob/main/spring-batch-samples/src/main/java/org/springframework/batch/samples/football/README.md) to learn more.


## How to run

### Prepare environment variables
* Navigate to the root folder of this Java sample project from the root of git repo.
```bash
cd job-samples/football
```
* Create environment variables file `setup-env-variables.sh` based on template. 
```bash
cp ./setup-env-variables-template.sh setup-env-variables.sh -i
```

* Set subscription id to variable `SUBSCRIPTION`, desired name of resource group and service instance to variables `RESOURCE_GROUP` and `SPRING_APPS_SERVICE`.
```bash
source ./setup-env-variables.sh
az account set --subscription ${SUBSCRIPTION}
```

### Create Azure Spring Apps Service

Please ensure you have an Azure Spring Apps service instance in Enterprise plan ready for use. Reference [Quickstarts](https://learn.microsoft.com/azure/spring-apps/enterprise/quickstart-deploy-web-app?pivots=sc-enterprise&tabs=Azure-portal%2CAzure-portal-ent) to create Azure Spring Apps instance. Build service is required in this tutorial and please enable it when you create service instance. 

You need to [set up a log analytics workspace](https://learn.microsoft.com/azure/spring-apps/basic-standard/quickstart-setup-log-analytics) to query data in logs.

### Create and Execute Job

* Clear out the existing classes from your sample project.
```bash
mvn clean
```

* Create the job.
```bash
az spring job create -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} --name football
```

* Deploy this football sample project to job. It uploads and compiles the source code on Azure and makes it ready to start. 
```bash
az spring job deploy -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} --name football --source-path . --build-env BP_JVM_VERSION=17.*
```

* Start job and set execution name to the variable `EXECUTION_NAME`.
```bash
export EXECUTION_NAME=$(az spring job start -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} --name football --query name -o tsv)
```

### Query job execution log

It takes several minutes to show logs of the job execution in log analytics workspace.

* Query execution result according to job name and its execution name.
```bash
az spring job execution show -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} --job-name football --job-execution-name ${EXECUTION_NAME}
```

* Fetch resource id of the log analytics workspace. The script below assumes the first workspace is serving the current ASA service instance. If there are multiple workspaces, please set the correct resource id to `WORKSPACE_ID`
```bash
export SPRING_APPS_RESOURCE_ID=$(az spring show -g ${RESOURCE_GROUP} -n ${SPRING_APPS_SERVICE} --query id -o tsv)

export WORKSPACE_ID=$(az monitor diagnostic-settings list -g ${RESOURCE_GROUP} --resource ${SPRING_APPS_SERVICE} --resource-type Microsoft.AppPlatform/Spring --query '[0].workspaceId' -o tsv)
```

* Query execution log from the log analytics workspace.
```bash
export CUSTOMER_ID=$(az monitor log-analytics workspace show --ids ${WORKSPACE_ID} --query customerId -o tsv)

az monitor log-analytics query -w ${CUSTOMER_ID} --analytics-query "AppPlatformLogsforSpring | where AppName == '${EXECUTION_NAME}' | order by TimeGenerated asc" --query '[].{Time:TimeGenerated, Log:Log}' --output table
```
