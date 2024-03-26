# Hello World

## About the sample
This sample project demonstrates the fundamental features of job in Azure Spring Apps service.

## How to run

### Prepare environment variables
* Navigate to the root folder of this Java sample project from the root of git repo.
```bash
cd job-samples/hello-world
```
* Create environment variables file `setup-env-variables.sh` based on template. 
```bash
cp ./setup-env-variables-template.sh setup-env-variables.sh -i
```

* Set subscription id to variable `SUBSCRIPTION`, desired name of resource group and service instance to variables `RESOURCE_GROUP` and `SPRING_APPS_SERVICE`.
```bash
source ./setup-env-variables.sh
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
az spring job create -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} --name hello-world-job
```

* Deploy this football sample project to job. It uploads and compiles the source code on Azure and makes it ready to start. 
```bash
az spring job deploy -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} --name hello-world-job --source-path . --build-env BP_JVM_VERSION=17.*
```

* Start job with custom environment variables and arguments.
```bash
az spring job start -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} --name hello-world-job --envs JOB_ENV_ATTR1=value1 JOB_ENV_ATTR2=value2 --secret-envs JOB_ENV_SECRET1=mysecret --args "test a b --sleep=5 c:d a"
```
* List execution results
```
az spring job execution list -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} --job-name hello-world-job --query '[].{startTime:startTime, endTime:endTime, name:name, status:status}' --output table
```

### Query job execution log

* Get the execution name of the last execution.
```bash
export EXECUTION_NAME=$(az spring job execution list -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} --job-name hello-world-job --query '[-1].name' -o tsv)
```

* Fetch resource id of the log analytics workspace. The script below assumes the first workspace is serving the current ASA service instance. If there are multiple workspaces, please set the correct resource id to `WORKSPACE_ID`
```bash
export SPRING_APPS_RESOURCE_ID=$(az spring show -g ${RESOURCE_GROUP} -n ${SPRING_APPS_SERVICE} --query id -o tsv)

export WORKSPACE_ID=$(az monitor diagnostic-settings list -g ${RESOURCE_GROUP} --resource ${SPRING_APPS_SERVICE} --resource-type Microsoft.AppPlatform/Spring --query [0].workspaceId -o tsv)
```

* Query execution log from the log analytics workspace.
```bash
export CUSTOMER_ID=$(az monitor log-analytics workspace show --ids ${WORKSPACE_ID} --query customerId -o tsv)

az monitor log-analytics query -w ${CUSTOMER_ID} --analytics-query "AppPlatformLogsforSpring | where AppName == '${EXECUTION_NAME}' | order by TimeGenerated asc" --query '[].{Time:TimeGenerated, Log:Log}' --output table
```

### Cancel an executing job

* Start job with default settings. It will sleep for 30 seconds to imitate the real job.
```bash
export EXECUTION_NAME=$(az spring job start -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} --name hello-world-job --query name -o tsv)
```

* Cancel the executing job by name
```bash
az spring job execution cancel -g ${RESOURCE_GROUP} -s ${SPRING_APPS_SERVICE} --job-name hello-world-job --job-execution-name ${EXECUTION_NAME}
```