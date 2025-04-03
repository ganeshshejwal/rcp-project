package com.cennox.rcp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cennox.rcp.entity.Acquirer;
import com.cennox.rcp.repository.AcquirerRepository;
import com.cennox.rcp.service.RcpService;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import jakarta.annotation.PostConstruct;

import com.cennox.rcp.entity.Device;
import com.cennox.rcp.repository.DeviceRepository;

@Service
public class RcpServiceImpl implements RcpService {

    private Logger logger = LoggerFactory.getLogger(RcpServiceImpl.class);


   
    private final HazelcastInstance hazelcastInstance;
    
    private final DeviceRepository deviceRepository;
    
    private final AcquirerRepository acquirerRepository;

    public RcpServiceImpl(DeviceRepository deviceRepository, AcquirerRepository acquirerRepository, HazelcastInstance hazelcastInstance) {
        this.acquirerRepository = acquirerRepository;
        this.deviceRepository = deviceRepository;
        this.hazelcastInstance = hazelcastInstance;
    }
    

    @PostConstruct
    public void preloadAcquirerCache() {
    logger.info("The Acquirer Data is loading from Database to cache");
    IMap<UUID, Acquirer> cache = hazelcastInstance.getMap("acquirer");
    List<Acquirer> acquirer = acquirerRepository.findAll();
         for (Acquirer acq : acquirer) {
             cache.put(acq.getAcquirerId(), acq);
        }
    }
    
    @PostConstruct
    public void preloadDeviceCache() {
        logger.info("The Device Data is loading from Database to cache");
        IMap<UUID, Device> cache = hazelcastInstance.getMap("device");
        List<Device> devices = deviceRepository.findAll();
        for (Device device : devices) {
            cache.put(device.getDeviceId(), device);
        }
    }


    @Override
    public Device createDevice(Device device) {
        IMap<UUID, Device> cache = hazelcastInstance.getMap("device");
        cache.put(device.getDeviceId(), device);
        return device;
    }

    @Override
    public Device getDeviceById(UUID deviceId) {
        IMap<UUID, Device> cache = hazelcastInstance.getMap("device");
        return cache.get(deviceId);
    }

    @Override
    public List<Device> getAllDevices() {
        IMap<UUID, Device> cache = hazelcastInstance.getMap("device");
        return new ArrayList<>(cache.values());
    }

    @Override
    public Device updateDevice(UUID deviceId, Device device) {
        Device existingDevice = getDeviceById(deviceId);
        existingDevice.setTerminalId(device.getTerminalId());
        existingDevice.setMerchantId(device.getMerchantId());
        existingDevice.setDeviceType(device.getDeviceType());
        existingDevice.setLocation(device.getLocation());
        return deviceRepository.save(existingDevice);
    }

    @Override
    public String deleteDevice(UUID deviceId) {
        IMap<UUID, Device> cache = hazelcastInstance.getMap("device");
        cache.remove(deviceId);
        return "Device Deleted Sucessfully";
    }



    @Override
    public Acquirer createAcquirer(Acquirer acquirer) {
        IMap<UUID, Acquirer> cache = hazelcastInstance.getMap("acquirer");
         cache.put(acquirer.getAcquirerId(), acquirer);
         return acquirer;
    }

    // @Override
    // public Acquirer updateAcquirer(Acquirer acquirer) {
    //     Optional<Acquirer> existingAcquirer = acquirerRepository.findByAcquirerId(acquirer.getAcquirerId());
    //     if(existingAcquirer.isEmpty()) {
    //        logger.info("Acquirer not found");
    //        return null;
    //     }
    //     existingAcquirer.get().setAcquirerName(acquirer.getAcquirerName());
    //     existingAcquirer.get().setCode(acquirer.getCode());
    //     existingAcquirer.get().setCountry(acquirer.getCountry());
    //     existingAcquirer.get().setCurrency(acquirer.getCurrency());
    //     return acquirerRepository.save(existingAcquirer.get());
        
    // }

    @Override
    public Acquirer getAcquirerById(UUID acquirerId) {
        IMap<UUID, Acquirer> cache = hazelcastInstance.getMap("acquirer");
        return cache.get(acquirerId);
         
    }

    @Override
    public List<Acquirer> getAllAcquirers() {
        IMap<UUID, Acquirer> cache = hazelcastInstance.getMap("acquirer");
        return new ArrayList<>(cache.values());
    }

    @Override
    public void deleteAcquirer(UUID acquirerId) {
        IMap<UUID, Acquirer> cache = hazelcastInstance.getMap("acquirer");
        cache.remove(acquirerId);
    }

}
