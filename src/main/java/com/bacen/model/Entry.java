package com.bacen.model;

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

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * Entry is a class containing the details of an user account associated to a unique key.
 */
@Entity(name = "bacen.Entry")
@Table(name = "directory")
public class Entry extends PanacheEntityBase implements ICPF, IBankingDomicile, IPixKey {

    public enum KeyType { CPF, EMAIL, PHONE }

    @Column(name = "owner")
    public String owner;
    
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    @JsonProperty(value = "creation_date")
    public Date creationDate;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private PixKey pixKey;

    @Embedded
    private BankingDomicile bankingDomicile;
    
    @Embedded
    private CPF cpf;

    /**
     * 
     */
    public Entry() {}
    
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
        this.id = id;
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
