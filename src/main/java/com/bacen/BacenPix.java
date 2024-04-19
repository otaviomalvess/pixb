package com.bacen;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pix_request")
public class BacenPix extends PanacheEntityBase {
    
    enum ResolvedStates { REQUEST, FAIL, SUCCESS }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "end_to_end_id")
    @JsonProperty(value = "end_to_end_id")
    public long endToEndId;

    @Column(name = "bank")
    public String bank;

    @Column(name = "branch")
    public String branch;

    @Column(name = "account")
    public String account;

    @Column(name = "owner")
    public String owner;

    @Column(name = "cpf")
    public String cpf;

    @Column(name = "value")
    public double value;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "resolved")
    public ResolvedStates resolved;
}
