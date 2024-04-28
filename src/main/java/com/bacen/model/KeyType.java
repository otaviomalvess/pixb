package com.bacen.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "key_type")
public class KeyType extends PanacheEntityBase {
    
    enum Types { CPF, EMAIL, PHONE, RANDOM }

    @Id
    @Column(name = "id")
    public short id;

    @Column(name = "type")
    public String type;
}
