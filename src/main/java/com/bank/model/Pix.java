package com.bank.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.util.common.IBankingDomicile;
import com.util.common.ICPF;
import com.util.common.IPix;
import com.util.model.BankingDomicile;
import com.util.model.CPF;

/**
 * Pix is a class containing the details and state of a pix transfer request.
 */
public class Pix implements IBankingDomicile, ICPF, IPix {
    
    public enum ResolvedStates { REQUEST, FAIL, SUCCESS }

    @JsonProperty(value = "end_to_end_id")
    public long endToEndId;
    
    public String owner;
    
    public double value;
    
    public ResolvedStates resolved;
    
    private BankingDomicile bankingDomicile;
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
        this.bankingDomicile = new BankingDomicile(bank, branch, account);
        this.cpf = new CPF(cpf);
    }

    public Pix(
            final long endToEndId,
            final String owner,
            final double value,
            final ResolvedStates resolved,
            final BankingDomicile bankingDomicile,
            final String cpf
    ) {
        if (owner == null)
            throw new NullPointerException("Owner cannot be null");
        if (owner.isBlank())
            throw new IllegalArgumentException("Owner cannot be blank");
        
        this.endToEndId = endToEndId;
        this.owner = owner;
        this.value = value;
        this.bankingDomicile = bankingDomicile; 
        this.cpf = new CPF(cpf);
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
