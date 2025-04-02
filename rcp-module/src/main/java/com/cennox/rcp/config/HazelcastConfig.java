package com.cennox.rcp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.cennox.rcp.hazelcast.AcquirerMapStore;
import com.cennox.rcp.hazelcast.DeviceMapStore;
import com.hazelcast.config.Config;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@Configuration

public class HazelcastConfig {
    @Bean
    @Lazy
    public HazelcastInstance hazelcastInstance(AcquirerMapStore acquirerMapStore, DeviceMapStore deviceMapStore) {
        Config config = new Config();
        config.setInstanceName("hazelcast-instance");

        MapStoreConfig acquirerMapStoreConfig = new MapStoreConfig()
                .setImplementation(acquirerMapStore)
                .setWriteDelaySeconds(0)
                .setInitialLoadMode(MapStoreConfig.InitialLoadMode.EAGER);

        MapConfig acquirerMapConfig = new MapConfig("acquirer")
                .setMapStoreConfig(acquirerMapStoreConfig);

        MapStoreConfig deviceMapStoreConfig = new MapStoreConfig()
                .setImplementation(deviceMapStore)
                .setWriteDelaySeconds(0)
                .setInitialLoadMode(MapStoreConfig.InitialLoadMode.EAGER);

        MapConfig deviceMapConfig = new MapConfig("device")
                .setMapStoreConfig(deviceMapStoreConfig);

        config.addMapConfig(acquirerMapConfig);
        config.addMapConfig(deviceMapConfig);

        config.setClusterName("rcp-cluster");
        config.getNetworkConfig().setPort(5701).setPortAutoIncrement(true).setPortCount(2);
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true)
                .addMember("localhost:5701")
                .addMember("localhost:5702");

        ManagementCenterConfig managementCenterConfig = new ManagementCenterConfig();
        managementCenterConfig.setConsoleEnabled(true);
        managementCenterConfig.setScriptingEnabled(true);
        config.setManagementCenterConfig(managementCenterConfig);

        return Hazelcast.newHazelcastInstance(config);

    }

}
