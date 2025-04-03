package com.cennox.hazelcast_server.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.cennox.hazelcast_server.entity.Acquirer;

@Repository
public interface AcquirerRepository extends JpaRepository<Acquirer, UUID> {

    
} 
