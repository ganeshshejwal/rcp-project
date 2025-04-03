package com.cennox.hazelcast_server.repository;

import com.cennox.hazelcast_server.entity.Device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;


@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {

}
