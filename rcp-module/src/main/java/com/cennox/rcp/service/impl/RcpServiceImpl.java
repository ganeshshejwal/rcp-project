package com.cennox.rcp.service.impl;

import java.util.ArrayList;
import java.util.List;

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


    @Autowired
    private HazelcastInstance hazelcastInstance;
    
    private final DeviceRepository deviceRepository;
    
    private final AcquirerRepository acquirerRepository;

    public RcpServiceImpl(DeviceRepository deviceRepository, AcquirerRepository acquirerRepository) {
        this.acquirerRepository = acquirerRepository;
        this.deviceRepository = deviceRepository;
    }
    

    @PostConstruct
    public void preloadAcquirerCache() {
    logger.info("The Acquirer Data is loading from Database to cache");
    IMap<Long, Acquirer> cache = hazelcastInstance.getMap("acquirer");
    List<Acquirer> acquirer = acquirerRepository.findAll();
         for (Acquirer acq : acquirer) {
             cache.put(acq.getCacheId(), acq);
        }
    }
    
    @PostConstruct
    public void preloadDeviceCache() {
    logger.info("The Device Data is loading from Database to cache");
    IMap<Long, Device> cache = hazelcastInstance.getMap("device");
    List<Device> device = deviceRepository.findAll();
         for (Device dev : device) {
             cache.put(dev.getCacheId(), dev);
        }
    }


    @Override
    public Device createDevice(Device device) {
        IMap<Long, Device> cache = hazelcastInstance.getMap("device");
        cache.put(device.getCacheId(), device);
        return device;
    }

    @Override
    public Device getDeviceById(Long cacheId) {
        IMap<Long, Device> cache = hazelcastInstance.getMap("device");
        return cache.get(cacheId);
    }

    @Override
    public List<Device> getAllDevices() {
        IMap<Long, Device> cache = hazelcastInstance.getMap("device");
        return new ArrayList<>(cache.values());
    }

    // @Override
    // public Device updateDevice(UUID id, Device device) {
    //     Device existingDevice = getDeviceById(id);
    //     existingDevice.setTerminalId(device.getTerminalId());
    //     existingDevice.setMerchantId(device.getMerchantId());
    //     existingDevice.setDeviceType(device.getDeviceType());
    //     existingDevice.setLocation(device.getLocation());
    //     return deviceRepository.save(existingDevice);
    // }

    @Override
    public String deleteDevice(Long cacheId) {
        IMap<Long, Device> cache = hazelcastInstance.getMap("device");
        cache.remove(cacheId);
        return "Device Deleted Sucessfully";
    }



    @Override
    public Acquirer createAcquirer(Acquirer acquirer) {
        IMap<Long, Acquirer> cache = hazelcastInstance.getMap("acquirer");
         cache.put(acquirer.getCacheId(), acquirer);
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
    public Acquirer getAcquirerById(Long cacheId) {
        IMap<Long, Acquirer> cache = hazelcastInstance.getMap("acquirer");
        return cache.get(cacheId);
         
    }

    @Override
    public List<Acquirer> getAllAcquirers() {
        IMap<Long, Acquirer> cache = hazelcastInstance.getMap("acquirer");
        return new ArrayList<>(cache.values());
    }

    @Override
    public void deleteAcquirer(Long cacheId) {
        IMap<Long, Acquirer> cache = hazelcastInstance.getMap("acquirer");
        cache.remove(cacheId);
    }

}
