package com.cennox.hazelcast_server.hazelcast;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.cennox.hazelcast_server.entity.Device;
import com.cennox.hazelcast_server.repository.DeviceRepository;
import com.hazelcast.map.MapStore;

@Component
public class DeviceMapStore implements MapStore<Long, Device> {
    
    private DeviceRepository deviceRepository;

    public DeviceMapStore(@Lazy DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    private Logger logger = LoggerFactory.getLogger(DeviceMapStore.class);

    @Override
    public Device load(Long key) {
        logger.info("Fetching data for key: {} from DB", key);
        return deviceRepository.findById(key).orElse(null);
    }

    @Override
    public Map<Long, Device> loadAll(Collection<Long> keys) {
        Map<Long, Device>  map = new HashMap<>();
        List<Device> devices = deviceRepository.findAllById(keys);
        for (Device device : devices) {
            map.put(device.getCacheId(), device);
        }
        logger.info("Fetching multiple keys from DB: {}", keys);
        return map;
    }

    @Override
    public Iterable<Long> loadAllKeys() {
        List<Long> allKeys = deviceRepository.findAll().stream().map(Device::getCacheId).toList();
        logger.info("Keys loaded: {}", allKeys);
        return allKeys;
    }

    @Override
    public void store(Long key, Device value) {
        logger.info("Storing data for key: {} in DB", key);
        deviceRepository.save(value);
    }

    @Override
    public void storeAll(Map<Long, Device> map) {
        deviceRepository.saveAll(map.values());
    }

    @Override
    public void delete(Long key) {
        logger.info("Deleting data for key: {} from DB", key);
        deviceRepository.deleteById(key);
    }

    @Override
    public void deleteAll(Collection<Long> keys) {
        keys.forEach(deviceRepository::deleteById);
    }

}

