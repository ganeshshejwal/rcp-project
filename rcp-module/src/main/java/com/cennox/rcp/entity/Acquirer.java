package com.cennox.rcp.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "acquirer")
public class Acquirer implements Serializable{
    
    @Id
    @Column(name = "cache_id")
    private Long cacheId; 
 
    private String acquirerName;
 
    private String code;
 
    private String country;
 
    private String currency;

}
