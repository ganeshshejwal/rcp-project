package com.cennox.rcp.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cennox.rcp.entity.Acquirer;
import com.cennox.rcp.entity.Device;
import com.cennox.rcp.service.RcpService;

@RestController
@RequestMapping("/api/rcp")
public class RcpController {

    private final RcpService rcpService;

    public RcpController(RcpService rcpService) {
        this.rcpService = rcpService;
    }

    @PostMapping("/device/create")
    public ResponseEntity<Device> createDevice(@RequestBody Device device) {
        return ResponseEntity.ok(rcpService.createDevice(device));
    }

    @GetMapping("/device/get/{device_id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable(name = "device_id") UUID deviceId) {
        Device device = rcpService.getDeviceById(deviceId);
        return ResponseEntity.ok(device);
    }

    @GetMapping("/device/get-all")
    public ResponseEntity<List<Device>> getAllDevices() {
        return ResponseEntity.ok(rcpService.getAllDevices());
    }

    @PutMapping("/device/update/{device_id}")
    public ResponseEntity<Device> updateDevice(@PathVariable(name = "device_id") UUID deviceId, @RequestBody Device device) {
        return ResponseEntity.ok(rcpService.updateDevice(deviceId, device));
    }

    @DeleteMapping("/device/delete/{device_id}")
    public ResponseEntity<String> deleteDevice(@PathVariable(name = "device_id") UUID deviceId) {
        return ResponseEntity.ok(rcpService.deleteDevice(deviceId));
    }

    @PostMapping("/acquirer/create")
    public ResponseEntity<Acquirer> createAcquirer(@RequestBody Acquirer acquirer) {
        Acquirer savedAcquirer = rcpService.createAcquirer(acquirer);
        return ResponseEntity.status(201).body(savedAcquirer);
    }

    @GetMapping("/acquirer/{cacheId}")
    public Acquirer getAcquirerById(@PathVariable(name = "cacheId") Long cacheId) {
        return rcpService.getAcquirerById(cacheId);

    }

    @GetMapping("/acquirer/getAll")
    public List<Acquirer> getAllAcquirers() {
        return rcpService.getAllAcquirers();
    }

    // @PutMapping("/acquirer/update")
    // public Acquirer updateAcquirer(@RequestBody Acquirer acquirer) {
    // return rcpService.updateAcquirer(acquirer);
    // }

    @DeleteMapping("/acquirer/Delete/{cacheId}")
    public String deleteAcquirer(@PathVariable(name = "cacheId") Long cacheId) {
        rcpService.deleteAcquirer(cacheId);
        return "Acquirer deleted successfully";
    }

}
