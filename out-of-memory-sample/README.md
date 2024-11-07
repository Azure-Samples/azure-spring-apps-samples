# Out-of-Memory-Samples

This sample project is used to show 3 types of circumstances under which out of memory exceptions occur in spring-boot
applications of Azure Spring Cloud.

- **Out of direct memory for violating JVM options**:
  The direct memory space assigned as JVM options exhausted.

- **Out of on-heap memory for violating JVM options**:
  The heap memory space assigned as JVM options exhausted.

- **Out of direct memory leading to system memory running out**:
  Direct memory being requested constantly, the increasement of direct memory exhausted pod memory so that system memory
  runs out.


