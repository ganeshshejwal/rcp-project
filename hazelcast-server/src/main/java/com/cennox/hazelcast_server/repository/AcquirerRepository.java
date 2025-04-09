package com.cennox.hazelcast_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cennox.sharedlibs.entity.Acquirer;

import java.util.UUID;

@Repository
public interface AcquirerRepository extends JpaRepository<Acquirer, UUID> {

    
} 
