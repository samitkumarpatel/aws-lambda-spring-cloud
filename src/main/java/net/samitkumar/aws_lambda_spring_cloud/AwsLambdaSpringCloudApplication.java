package net.samitkumar.aws_lambda_spring_cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Map;

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
}

@RestController
class WebController {

	@GetMapping("/ping")
	Map<String, Object> ping() {
		return Map.of("pong", "Hello World");
	}

}


