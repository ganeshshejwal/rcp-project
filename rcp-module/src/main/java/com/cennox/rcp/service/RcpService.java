package com.cennox.rcp.service;

import java.util.List;
import java.util.UUID;

import com.cennox.rcp.entity.Acquirer;
  
import com.cennox.rcp.entity.Device;

public interface RcpService {

    Device createDevice(Device device);

    Device getDeviceById(UUID deviceId);

    List<Device> getAllDevices();

    Device updateDevice(UUID deviceId, Device device);

    String deleteDevice(UUID deviceId);

    public Acquirer createAcquirer(Acquirer acquirer);

    // public Acquirer updateAcquirer(Acquirer acquirer);
    
    public Acquirer getAcquirerById(UUID acquirerId);

    public List<Acquirer> getAllAcquirers();

    public void deleteAcquirer(UUID acquirerId);


} 
