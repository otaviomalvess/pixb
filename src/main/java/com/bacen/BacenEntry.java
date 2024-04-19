package com.bacen;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "directory")
public class BacenEntry extends PanacheEntityBase {
    
    @Id
    @Column(name = "pkey")
    @JsonProperty(value = "pkey")
    public String key;
    
    @Column(name = "pkey_type")
    @JsonProperty(value = "pkey_type")
    public short keyType;

    @JsonProperty(value = "end_to_end_id")
    public String endToEndId;

    @Column(name = "bank")
    public String bank;

    @Column(name = "branch")
    public String branch;

    @Column(name = "account")
    public String account;

    @Column(name = "cpf")
    public String cpf;

    @Column(name = "owner")
    public String owner;

    @Column(name = "creation_date")
    @JsonProperty(value = "creation_date")
    public Date creationDate;
}
