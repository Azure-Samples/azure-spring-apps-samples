## Config Server Git Repository `application.yml` Samples.

This folder holds some samples of `application.yml` files, which can be imported into [Azure Spring Cloud](https://azure.microsoft.com/en-us/services/spring-cloud/) `Config Server` blade. For more details, please see [Import Settings from YAML File](https://docs.microsoft.com/en-us/azure/spring-cloud/spring-cloud-tutorial-config-server#enter-repository-information-into-a-yaml-file).

### Startup

The startup [config-server-startup.yml](./config-server-startup.yml) file for `Config Server` is very simple, which contains only one property named `uri`. The uri specifies one public `uri` of one git repository.

### Http Basic Authentication

The http basic authentication [config-server-http-basic-auth.yml](./config-server-http-basic-auth.yml) file for `Config Server` focus on access private git repository with [Http Basic Authentication](https://en.wikipedia.org/wiki/Basic_access_authentication). Of course, the `username` and the `password` should be provided from the yaml file.

### SSH Authentication

The ssh authentication [config-server-ssh-auth.yml](./config-server-ssh-auth.yml) file for `Config Server` focus on access private git repository with [SSH](./https://en.wikipedia.org/wiki/Secure_Shell). Almost the same as `Http Basic Authentication`, at least the `private key` of `SSH` should be provided.

### Pattern

The more complex like pattern matching on the application and profile name is also supported. The pattern format is a comma-separated list of `{application}/{profile}` names with wildcards, see [config-server-pattern.yml](./config-server-pattern.yml).

### Reference

1. https://cloud.spring.io/spring-cloud-static/spring-cloud-config/2.1.4.RELEASE/single/spring-cloud-config.html#_git_backend
2. https://cloud.spring.io/spring-cloud-static/spring-cloud-config/2.1.4.RELEASE/single/spring-cloud-config.html#_authentication
3. https://cloud.spring.io/spring-cloud-static/spring-cloud-config/2.1.4.RELEASE/single/spring-cloud-config.html#_git_ssh_configuration_using_properties
4. https://cloud.spring.io/spring-cloud-static/spring-cloud-config/2.1.4.RELEASE/single/spring-cloud-config.html#_pattern_matching_and_multiple_repositories