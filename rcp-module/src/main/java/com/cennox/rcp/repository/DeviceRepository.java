package com.cennox.rcp.repository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cennox.sharedlibs.entity.Device;


@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {

}
