package com.bank.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
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
public class Entry implements IBankingDomicile, ICPF, IPixKey {
    
    @JsonProperty(value = "pkey")
    public String key;
    
    @JsonProperty(value = "pkey_type")
    public short keyType;

    @JsonProperty(value = "end_to_end_id")
    public long endToEndId;

    public String owner;
    
    @JsonProperty(value = "creation_date")
    public Date creationDate;
    
    @Embedded
    private PixKey pixKey;

    @Embedded
    private BankingDomicile bankingDomicile;

    @Embedded
    private CPF cpf;

    /**
     * 
     */
    @JsonCreator(mode = Mode.PROPERTIES)
    public Entry(
            @JsonProperty final long id,
            @JsonProperty final String owner,
            @JsonProperty(value = "creation_date") final Date creationDate,
            @JsonProperty final String key,
            @JsonProperty(value = "key_type") final PixKey.Types keyType,
            @JsonProperty final int bank,
            @JsonProperty final int branch,
            @JsonProperty final int account,
            @JsonProperty final String cpf
    ) {
        this.owner = owner;
        this.pixKey = new PixKey(key, keyType);
        this.bankingDomicile = new BankingDomicile(bank, branch, account);
        this.cpf = new CPF(cpf);
    }
    
    public String getCPF() {
        return cpf.value;
    }

    public BankingDomicile getBankingDomicile() {
        return bankingDomicile;
    }

    public String getKey() {
        return pixKey.key;
    }
}
