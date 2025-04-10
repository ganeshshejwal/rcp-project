package com.cennox.config_loader.job;

import com.cennox.config_loader.repository.AcquirerRepository;
import com.cennox.config_loader.repository.DeviceRepository;
import com.cennox.sharedlibs.entity.Acquirer;
import com.cennox.sharedlibs.entity.Device;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import lombok.extern.slf4j.Slf4j;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class CacheLoaderJob implements Job {

    private HazelcastInstance hazelcastInstance;

    private DeviceRepository deviceRepository; 

    private AcquirerRepository acquirerRepository;

    public CacheLoaderJob(HazelcastInstance hazelcastInstance, DeviceRepository deviceRepository, AcquirerRepository acquirerRepository) {
        this.hazelcastInstance = hazelcastInstance;
        this.deviceRepository = deviceRepository;
        this.acquirerRepository = acquirerRepository;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Starting cache loading job...");

        IMap<UUID, Device> deviceMap = hazelcastInstance.getMap("device");
        List<Device> devices = deviceRepository.findAll(); 
        devices.forEach(device -> deviceMap.put(device.getDeviceId(), device));
        
        IMap<UUID, Acquirer> acquireMap = hazelcastInstance.getMap("acquirer");
        List<Acquirer> acquirers = acquirerRepository.findAll(); 
        acquirers.forEach(acquire -> acquireMap.put(acquire.getAcquirerId(), acquire));
        
        log.info("Hazelcast cache refreshed at: {}" , java.time.LocalDateTime.now());
    }
}
