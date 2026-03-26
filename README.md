# aws-lambda-spring-cloud

- [Docs](https://docs.spring.io/spring-cloud-function/reference/adapters/aws-intro.html).
- [Example](https://github.com/spring-cloud/spring-cloud-function/blob/main/spring-cloud-function-samples).

### Web based spring cloud on aws lambda
[Documentation to be followed](https://docs.spring.io/spring-cloud-function/reference/adapters/aws-intro.html#serverless-java-container).

- Deploy the generate jar during `./mvnw clean install`
- After Deploy make sure to change the handler to `com.amazonaws.serverless.proxy.spring.SpringDelegatingLambdaContainerHandler`
- Pass a Env variable `MAIN_CLASS=net.samitkumar.aws_lambda_spring_cloud.AwsLambdaSpringCloudApplication`
