package com.cennox.rcp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;

@Configuration
public class HazelcastClientConfig {
   @Bean
    public HazelcastInstance hazelcastClientInstance() {
        ClientConfig clientConfig = new ClientConfig();

        clientConfig.setClusterName("rcp-cluster");
        clientConfig.getNetworkConfig().addAddress("localhost:5701", "localhost:5702");

               
        return HazelcastClient.newHazelcastClient(clientConfig);
    }
}