package com.cennox.rcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.cennox.sharedlibs.entity")
public class RcpApplication {

	public static void main(String[] args) {
		SpringApplication.run(RcpApplication.class, args);
	}

}
