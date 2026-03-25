package net.samitkumar.aws_lambda_spring_cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

@SpringBootApplication
public class AwsLambdaSpringCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsLambdaSpringCloudApplication.class, args);
	}

	//This does not work in lambda!
	@Bean
	RouterFunction<ServerResponse> routerFunctions() {
		return RouterFunctions
				.route()
				.GET("/functional/ping", request -> Mono.just(Map.of("pong","Hello Functional router")).flatMap(ServerResponse.ok()::bodyValue))
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
