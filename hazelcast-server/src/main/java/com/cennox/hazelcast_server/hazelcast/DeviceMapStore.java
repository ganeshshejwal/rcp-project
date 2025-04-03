package com.cennox.hazelcast_server.hazelcast;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.cennox.hazelcast_server.entity.Device;
import com.cennox.hazelcast_server.repository.DeviceRepository;
import com.hazelcast.map.MapStore;

@Component
public class DeviceMapStore implements MapStore<UUID, Device> {
    
    private DeviceRepository deviceRepository;

    public DeviceMapStore(@Lazy DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    private Logger logger = LoggerFactory.getLogger(DeviceMapStore.class);

    @Override
    public Device load(UUID key) {
        logger.info("Fetching data for key: {} from DB", key);
        return deviceRepository.findById(key).orElse(null);
    }

    @Override
    public Map<UUID, Device> loadAll(Collection<UUID> keys) {
        Map<UUID, Device>  map = new HashMap<>();
        List<Device> devices = deviceRepository.findAllById(keys);
        for (Device device : devices) {
            map.put(device.getDeviceId(), device);
        }
        logger.info("Fetching multiple keys from DB: {}", keys);
        return map;
    }

    @Override
    public Iterable<UUID> loadAllKeys() {
        List<UUID> allKeys = deviceRepository.findAll().stream().map(Device::getDeviceId).toList();
        logger.info("Keys loaded: {}", allKeys);
        return allKeys;
    }

    @Override
    public void store(UUID key, Device value) {
        logger.info("Storing data for key: {} in DB", key);
        deviceRepository.save(value);
    }

    @Override
    public void storeAll(Map<UUID, Device> map) {
        deviceRepository.saveAll(map.values());
    }

    @Override
    public void delete(UUID key) {
        logger.info("Deleting data for key: {} from DB", key);
        deviceRepository.deleteById(key);
    }

    @Override
    public void deleteAll(Collection<UUID> keys) {
        keys.forEach(deviceRepository::deleteById);
    }

}

