package com.bacen.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.util.common.IBankingDomicile;
import com.util.common.ICPF;
import com.util.common.IPixKey;
import com.util.model.BankingDomicile;
import com.util.model.CPF;
import com.util.model.PixKey;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entry is a class containing the details of an user account associated to a unique key.
 */
@Entity(name = "bacen.Entry")
@Table(name = "directory")
public class Entry extends PanacheEntityBase implements ICPF, IBankingDomicile, IPixKey {
    
    @Id
    @Column(name = "pkey")
    @JsonProperty(value = "pkey")
    public String key;
    
    @Column(name = "pkey_type")
    @JsonProperty(value = "pkey_type")
    public short keyType;

    @JsonProperty(value = "end_to_end_id")
    public String endToEndId;

    @Embedded
    private PixKey pixKey;

    @Embedded
    private BankingDomicile bankingDomicile;
    
    @Embedded
    private CPF cpf;


    public String getCPF() {
        return cpf.value;
    }

    @Column(name = "owner")
    public String owner;
    public BankingDomicile getBankingDomicile() {
        return bankingDomicile;
    }

    @Column(name = "creation_date")
    @JsonProperty(value = "creation_date")
    public Date creationDate;
    public String getKey() {
        return pixKey.key;
    }
}
