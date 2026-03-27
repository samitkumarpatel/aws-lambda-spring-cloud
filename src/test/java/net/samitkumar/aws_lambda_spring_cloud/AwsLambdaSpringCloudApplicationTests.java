package net.samitkumar.aws_lambda_spring_cloud;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class AwsLambdaSpringCloudApplicationTests {

	@Test
	void contextLoads() {
	}

}
