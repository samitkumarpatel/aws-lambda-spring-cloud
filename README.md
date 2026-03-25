# aws-lambda-spring-cloud

- [Docs](https://docs.spring.io/spring-cloud-function/reference/adapters/aws-intro.html).
- [Example](https://github.com/spring-cloud/spring-cloud-function/blob/main/spring-cloud-function-samples).

### nativeImage build 

```shell
./mvnw native:compile verify -Pnative
```
This will generate and native Image and zip it with a necessary file.
