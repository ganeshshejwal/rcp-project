package com.cennox.rcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "com.cennox.sharedlibs.entity")
@ComponentScan(basePackages = {
    "com.cennox.rcp",  
    "com.cennox.hazelcast_server"
})
public class RcpApplication {

	public static void main(String[] args) {
		SpringApplication.run(RcpApplication.class, args);
	}

}
