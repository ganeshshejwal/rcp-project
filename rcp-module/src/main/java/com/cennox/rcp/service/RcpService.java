package com.cennox.rcp.service;

import java.util.List;

import com.cennox.rcp.entity.Acquirer;
  
import com.cennox.rcp.entity.Device;

public interface RcpService {

    Device createDevice(Device device);

    Device getDeviceById(Long cacheId);

    List<Device> getAllDevices();

    // Device updateDevice(UUID id, Device device);

    String deleteDevice(Long cacheId);

    public Acquirer createAcquirer(Acquirer acquirer);

    // public Acquirer updateAcquirer(Acquirer acquirer);
    
    public Acquirer getAcquirerById(Long cacheId);

    public List<Acquirer> getAllAcquirers();

    public void deleteAcquirer(Long cacheId);


} 
