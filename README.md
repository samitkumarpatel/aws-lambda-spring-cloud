# aws-lambda-spring-cloud

- [Docs](https://docs.spring.io/spring-cloud-function/reference/adapters/aws-intro.html).
- [Example](https://github.com/spring-cloud/spring-cloud-function/blob/main/spring-cloud-function-samples).
- [Java on AWS Lambda](https://catalog.workshops.aws/java-on-aws-lambda/en-US/03-accelerate/graal-plain-java)

### nativeImage build 

```shell
./mvnw package -Pnative
# OR
./mvnw package
```
This will generate and native Image and zip it with a necessary file.

If you are missing a graal vm env, use this :
```shell
docker run -it --rm \
  -v $(pwd):$(pwd) -v ~/.m2:/root/.m2 \
  -w $(pwd) --entrypoint sh\
  ghcr.io/graalvm/native-image-community:25 ./mvnw package -Pnative
```


### Deploy to AWS Lambda
- Create a function. (choose the name you like)
- Runtime : Amazon Linux 2023
- Architecture: x86_64 or arm64 (choose the one you built for) 
```shell
uname -m #will give you a hints. Make sure to execute this where ever you have built the native image.
```
> Note:: If you have used the avobe graal vm docker image, you have built for x86_64 architecture.

- Handler: Point it to your main class (e.g. net.samitkumar.aws_lambda_spring_cloud.AwsLambdaSpringCloudApplication) - It's Optional
- Upload the zip file generated in the previous step.
- go to test tab and send this payload to test the function
```json
{
  "payload": "hello",
  "headers": {
    "function.name": "uppercase"
  }
}
```
