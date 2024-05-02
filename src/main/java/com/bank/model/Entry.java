package com.bank.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.util.common.IBankingDomicile;
import com.util.common.ICPF;
import com.util.common.IPixKey;
import com.util.model.BankingDomicile;
import com.util.model.CPF;
import com.util.model.PixKey;

import jakarta.persistence.Embedded;

/**
 * Entry is a class containing the details of an user account associated to a unique key.
 */
@Entity(name = "bank.Entry")
public class Entry implements IBankingDomicile, ICPF, IPixKey {
    
    @JsonProperty(value = "pkey")
    public String key;
    
    @JsonProperty(value = "pkey_type")
    public short keyType;

    @JsonProperty(value = "end_to_end_id")
    public String endToEndId;

    public int bank;
    @Embedded
    private PixKey pixKey;

    @Embedded
    private BankingDomicile bankingDomicile;

    @Embedded
    private CPF cpf;

    
    public String getCPF() {
        return cpf.value;
    }

    public String owner;
    public BankingDomicile getBankingDomicile() {
        return bankingDomicile;
    }

    @JsonProperty(value = "creation_date")
    public Date creationDate;
    public String getKey() {
        return pixKey.key;
    }
}
