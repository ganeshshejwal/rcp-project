package com.cennox.rcp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cennox.rcp.service.RcpService;
import com.cennox.sharedlibs.entity.Acquirer;
import com.cennox.sharedlibs.entity.Device;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RcpServiceImpl implements RcpService {

    private final HazelcastInstance hazelcastInstance;

    public RcpServiceImpl(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    private IMap<UUID, Device> getDeviceMap() {
        return hazelcastInstance.getMap("device");
    }

    private IMap<UUID, Acquirer> getAcquirerMap() {
        return hazelcastInstance.getMap("acquirer");
    }

    @Override
    public Device createDevice(Device device) {
        log.info("Start: createDevice()");
        UUID deviceId = UUID.randomUUID();
        device.setDeviceId(deviceId);
        getDeviceMap().put(deviceId, device);
        log.info("End: createDevice() - Device added with ID: {}", deviceId);
        return device;
    }

    @Override
    public Device getDeviceById(UUID deviceId) {
        log.info("Start: getDeviceById() - ID: {}", deviceId);
        Device device = getDeviceMap().get(deviceId);
        log.info("End: getDeviceById() - Found: {}", device != null);
        return device;
    }

    @Override
    public List<Device> getAllDevices() {
        log.info("Start: getAllDevices()");
        List<Device> devices = new ArrayList<>(getDeviceMap().values());
        log.info("End: getAllDevices() - Total: {}", devices.size());
        return devices;
    }

    @Override
    public Device updateDevice(UUID deviceId, Device device) {
        log.info("Start: updateDevice() - ID: {}", deviceId);
        Device existingDevice = getDeviceMap().get(deviceId);
        if (existingDevice == null) {
            log.warn("Device not found with ID: {}", deviceId);
            return null;
        }
        existingDevice.setTerminalId(device.getTerminalId());
        existingDevice.setMerchantId(device.getMerchantId());
        existingDevice.setDeviceType(device.getDeviceType());
        existingDevice.setLocation(device.getLocation());
        getDeviceMap().put(deviceId, existingDevice);
        log.info("End: updateDevice() - Device updated with ID: {}", deviceId);
        return existingDevice;
    }

    @Override
    public String deleteDevice(UUID deviceId) {
        log.info("Start: deleteDevice() - ID: {}", deviceId);
        getDeviceMap().remove(deviceId);
        log.info("End: deleteDevice() - Device removed");
        return "Device Deleted Successfully";
    }

    @Override
    public Acquirer createAcquirer(Acquirer acquirer) {
        log.info("Start: createAcquirer()");
        UUID acquirerId = UUID.randomUUID();
        acquirer.setAcquirerId(acquirerId);
        getAcquirerMap().put(acquirerId, acquirer);
        log.info("End: createAcquirer() - Acquirer added with ID: {}", acquirerId);
        return acquirer;
    }

    @Override
    public Acquirer getAcquirerById(UUID acquirerId) {
        log.info("Start: getAcquirerById() - ID: {}", acquirerId);
        Acquirer acquirer = getAcquirerMap().get(acquirerId);
        log.info("End: getAcquirerById() - Found: {}", acquirer != null);
        return acquirer;
    }

    @Override
    public List<Acquirer> getAllAcquirers() {
        log.info("Start: getAllAcquirers()");
        List<Acquirer> acquirers = new ArrayList<>(getAcquirerMap().values());
        log.info("End: getAllAcquirers() - Total: {}", acquirers.size());
        return acquirers;
    }

    @Override
    public void deleteAcquirer(UUID acquirerId) {
        log.info("Start: deleteAcquirer() - ID: {}", acquirerId);
        getAcquirerMap().remove(acquirerId);
        log.info("End: deleteAcquirer() - Acquirer removed");
    }
}
