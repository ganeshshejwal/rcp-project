package com.cennox.hazelcast_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.cennox.sharedlibs.entity")
public class HazelcastServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HazelcastServerApplication.class, args);
	}

}
