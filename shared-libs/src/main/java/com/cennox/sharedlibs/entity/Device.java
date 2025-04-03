package com.cennox.sharedlibs.entity;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "device")
public class Device implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "device_id")
    private UUID deviceId;  

    @Column(name = "terminal_id", nullable = false, unique = true, length = 20)
    private String terminalId;

    @Column(name = "merchant_id", nullable = false)
    private String merchantId;

    @Column(name = "device_type", nullable = false)
    private String deviceType; 

    @Column(name = "location", nullable = false)
    private String location;

}
