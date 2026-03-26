package net.samitkumar.aws_lambda_spring_cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Map;
import java.util.Objects;

@SpringBootApplication
public class AwsLambdaSpringCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsLambdaSpringCloudApplication.class, args);
	}

	@Bean
	RouterFunction<ServerResponse> routerFunctions() {
		return RouterFunctions
				.route()
				.GET("/functional/ping", request -> ServerResponse.ok().body(Map.of("pong","Hello Functional router")))
				.build();
	}
}

@RestController
class WebController {

	@GetMapping("/ping")
	Map<String, String> ping() {
		return Map.of("pong", "Hello World");
	}

	@PostMapping("/post")
	Map<String, String> ping(@RequestBody String message) {
		return Map.of("pong", Objects.nonNull(message) ? message : "Default Message");
	}
}
