package org.lucasnscr.awslocalstack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class AwsLocalstackApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsLocalstackApplication.class, args);
	}
}
