package com.bank.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/* This is a duplicate class from com.bacen.Entry.
    In a real world scenario, Bacen and Pixb would be 2 completely different systems, which would transfer data
    in JSON format. This class is here to simulate that.*/

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
