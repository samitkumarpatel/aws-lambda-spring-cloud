package net.samitkumar.aws_lambda_spring_cloud;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Map;
import java.util.function.Function;

@SpringBootApplication
@EnableWebMvc
public class AwsLambdaSpringCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsLambdaSpringCloudApplication.class, args);
	}

	@Bean
	RouterFunction<ServerResponse> routerFunction() {
		return RouterFunctions
				.route()
				.GET("/functional/ping", request -> ServerResponse.ok().body(Map.of("pong", "Hello functional router")))
				.build();
	}

	//TODO this is not working. make it work
	@Bean
	public Function<SQSEvent, String> processSQSEvent() {
		return SQSEvent::toString;
	}
}

@RestController
class WebController {

	@GetMapping("/ping")
	Map<String, Object> ping() {
		return Map.of("pong", "Hello World");
	}

}
