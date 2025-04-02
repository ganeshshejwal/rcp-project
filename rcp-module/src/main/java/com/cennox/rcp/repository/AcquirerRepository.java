package com.cennox.rcp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cennox.rcp.entity.Acquirer;

@Repository
public interface AcquirerRepository extends JpaRepository<Acquirer, Long> {

    
} 
