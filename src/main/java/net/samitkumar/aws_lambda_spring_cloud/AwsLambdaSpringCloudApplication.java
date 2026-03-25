package net.samitkumar.aws_lambda_spring_cloud;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class AwsLambdaSpringCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsLambdaSpringCloudApplication.class, args);
	}

	@Bean
	public Function<SQSEvent, String> processSQSEvent() {
		return SQSEvent::toString;
	}

}
