package com.bacen.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.util.model.BankingDomicile;
import com.util.model.CPF;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Pix is a class containing the details and state of a pix transfer request.
 */
@Entity(name = "bacen.Pix")
@Table(name = "pix_request")
public class Pix extends PanacheEntityBase {
    
    public enum ResolvedStates { REQUEST, FAIL, SUCCESS }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "end_to_end_id")
    @JsonProperty(value = "end_to_end_id")
    public long endToEndId;

    @Column(name = "owner")
    public String owner;


    @Column(name = "value")
    public double value;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "resolved")
    public ResolvedStates resolved;

    @Embedded
    private BankingDomicile bankingDomicile;

    @Embedded
    private CPF cpf;

}
