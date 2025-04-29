package com.babsman.vertexai;

import com.babsman.vertexai.config.GoogleCloudConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(GoogleCloudConfig.class)
public class MyVertexaiBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyVertexaiBackendApplication.class, args);
	}

}
