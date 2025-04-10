package com.cennox.config_loader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.cennox.sharedlibs.entity")
public class ConfigLoaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigLoaderApplication.class, args);
	}

}
