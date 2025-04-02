package com.cennox.rcp.repository;

import com.cennox.rcp.entity.Device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

}
