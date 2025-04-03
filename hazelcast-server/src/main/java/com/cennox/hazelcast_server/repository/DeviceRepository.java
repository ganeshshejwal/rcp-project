package com.cennox.hazelcast_server.repository;

import com.cennox.hazelcast_server.entity.Device;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {

}
