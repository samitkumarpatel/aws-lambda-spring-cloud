# aws-lambda-spring-cloud

- [Docs](https://docs.spring.io/spring-cloud-function/reference/adapters/aws-intro.html).
- [Example](https://github.com/spring-cloud/spring-cloud-function/blob/main/spring-cloud-function-samples).
- [Serverless Web](https://github.com/spring-cloud/spring-cloud-function/blob/main/spring-cloud-function-samples)

### Build
```shell
./mvnw clean install
```

### Deploy
- During deployment set the Handler: `com.amazonaws.serverless.proxy.spring.SpringDelegatingLambdaContainerHandler`
- Set the Environment Variables: `MAIN_CLASS=net.samitkumar.aws_lambda_spring_cloud.AwsLambdaSpringCloudApplication`
### Test from lambda
```json

```
