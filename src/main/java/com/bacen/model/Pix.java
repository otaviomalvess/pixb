package com.bacen.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.util.common.IBankingDomicile;
import com.util.common.ICPF;
import com.util.common.IPix;
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
public class Pix extends PanacheEntityBase implements IPix, IBankingDomicile, ICPF {
    
    public enum ResolvedStates { REQUEST, FAIL, SUCCESS }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "end_to_end_id")
    @JsonProperty(value = "end_to_end_id")
    private long endToEndId;

    @Column(name = "owner")
    private String owner;

    @Column(name = "value")
    private double value;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "resolved")
    public ResolvedStates resolved;

    @Embedded
    private BankingDomicile bankingDomicile;

    @Embedded
    private CPF cpf;

    /**
     * 
     */
    public Pix() {}

    @JsonCreator(mode = Mode.PROPERTIES)
    public Pix(
            @JsonProperty(value = "end_to_end_id") final long endToEndId,
            @JsonProperty final String owner,
            @JsonProperty final double value,
            @JsonProperty final ResolvedStates resolved,
            @JsonProperty final int bank,
            @JsonProperty final int branch,
            @JsonProperty final int account,
            @JsonProperty final String cpf
    ) {
        if (owner == null)
            throw new NullPointerException("Owner cannot be null");
        if (owner.isBlank())
            throw new IllegalArgumentException("Owner cannot be blank");

        this.endToEndId = endToEndId;
        this.owner = owner;
        this.value = value;
        this.resolved = resolved;
        this.cpf = new CPF(cpf);
        this.bankingDomicile = new BankingDomicile(bank, branch, account);
    }

    public void setResolvedState(final ResolvedStates resolved) {
        if (this.resolved != ResolvedStates.REQUEST)
            throw new IllegalStateException("Resolved state cannot be changed after resolving once.");
        
        this.resolved = resolved;
    }

    public long getEndToEndId() {
        return endToEndId;
    }

    public String getCPF() {
        return cpf.value;
    }

    public BankingDomicile getBankingDomicile() {
        return bankingDomicile;
    }
}
