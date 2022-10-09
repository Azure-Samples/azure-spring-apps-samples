---
page_type: sample
languages:
- java
products:
- azure-spring-cloud
description: "Sample projects for Azure Spring Cloud"
urlFragment: "azure-spring-cloud-samples"
---

# Azure Spring Cloud Samples

<!-- 
Guidelines on README format: https://review.docs.microsoft.com/help/onboard/admin/samples/concepts/readme-template?branch=master

Guidance on onboarding samples to docs.microsoft.com/samples: https://review.docs.microsoft.com/help/onboard/admin/samples/process/onboarding?branch=master

Taxonomies for products and languages: https://review.docs.microsoft.com/new-hope/information-architecture/metadata/taxonomies?branch=master
-->

## Overview

Azure Spring Cloud is a PaaS to help you easily run and operate Spring Cloud based microservices on Azure. The goal of Azure Spring Cloud is to let you focus on your code and let us take care of the operation work. Rich features are provided and some of the highlighted ones are:
- Managed native Spring Cloud Service Registry
- Managed native Spring Cloud Config Server
- Managed native Spring Cloud Distributed Tracing with Azure AppInsights
- Built-in metrics aggregration and monitoring
- Built-in logs aggregation and search
- Zero code change is required to run your Spring Boot apps in Azure Spring Cloud

You can find more details about Azure Spring Cloud in the [official documentation](https://docs.microsoft.com/en-us/azure/spring-cloud/).

> NOTE:
> Azure Spring Cloud is in Preview. You need to file a request at https://aka.ms/spring-cloud to access the service.
> The request will be reviewed and accepted on a case-by-case basis.

This repository contains samples projects for Azure Spring Cloud.
These sample projects show how to use various features in Azure Spring Cloud.
Below table shows the list of samples available in this repository.

| Folder                           | Description                                |
|----------------------------------|--------------------------------------------|
| [`custom-config-server-client`](./custom-config-server-client/) | Sample showing how to access Azure Spring Cloud managed Config Server through Azure RBAC when your applications are running outside Azure Spring Cloud |
| [`custom-eureka-client`](./custom-eureka-client/) | Sample showing how to access Azure Spring Cloud managed Spring Cloud Service Registry through Azure RBAC when your applications are running outside Azure Spring Cloud |
| [`managed-identity-keyvault`](./managed-identity-keyvault) | Sample project using system-assigned managed identity to access Key Vault |
| [`managed-identity-storage-blob`](./managed-identity-storage-blob) | Sample project using system-assigned managed identity to access Storage blob |
| [`service-binding-cosmosdb-mongo`](./service-binding-cosmosdb-mongo/) | Service binding sample with Azure CosmosDB Mongo API |
| [`service-binding-cosmosdb-sql`](./service-binding-cosmosdb-sql/) | Service binding sample with Azure CosmosDB SQL API   |
| [`service-binding-mysql`](./service-binding-mysql/) | Service binding sample with Azure Database for MySQL |
| [`service-binding-redis`](./service-binding-redis/) | Service binding sample with Azure Cache for Redis    |
| [`storage-sample`](./storage-sample/) | Sample showing how to use temporary and persistent storage in Azure Spring Cloud |
| [`java-11-sample`](./java-11-sample/) | Java 11 sample project to run in Azure Spring Cloud |
| [`java-8-sample`](./java-8-sample/) | Java 8 sample project to run in Azure Spring Cloud |

## Contributing

This project welcomes contributions and suggestions.  Most contributions require you to agree to a
Contributor License Agreement (CLA) declaring that you have the right to, and actually do, grant us
the rights to use your contribution. For details, visit https://cla.opensource.microsoft.com.

When you submit a pull request, a CLA bot will automatically determine whether you need to provide
a CLA and decorate the PR appropriately (e.g., status check, comment). Simply follow the instructions
provided by the bot. You will only need to do this once across all repos using our CLA.

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/).
For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or
contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.
