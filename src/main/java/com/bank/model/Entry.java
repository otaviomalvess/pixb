package com.bank.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Entry is a class containing the details of an user account associated to a unique key.
 */
@Entity(name = "bank.Entry")
public class Entry {
    
    @Id
    @JsonProperty(value = "pkey")
    public String key;
    
    @JsonProperty(value = "pkey_type")
    public short keyType;

    @JsonProperty(value = "end_to_end_id")
    public String endToEndId;

    public int bank;

    public int branch;

    public int account;

    public String cpf;

    public String owner;

    @JsonProperty(value = "creation_date")
    public Date creationDate;
}
