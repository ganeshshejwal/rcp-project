package com.cennox.hazelcast_server.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "acquirer")
public class Acquirer implements Serializable{
    
    @Id
    @Column(name = "acquirer_id")
    private UUID acquirerId; 
 
    private String acquirerName;
 
    private String code;
 
    private String country;
 
    private String currency;

}
