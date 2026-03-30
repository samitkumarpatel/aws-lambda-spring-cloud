package net.samitkumar.aws_lambda_spring_cloud;

import org.springframework.data.annotation.Id;

public record User(@Id Long id, String name, int age) {}

