package net.samitkumar.aws_lambda_spring_cloud;

import org.springframework.boot.SpringApplication;

public class TestDemoApplication {

    public static void main(String[] args) {
        SpringApplication.from(AwsLambdaSpringCloudApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
