package com.bank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    
    private BankingDomicile bankingDomicile;
    private CPF cpf;

    @JsonProperty(value = "pkey")
    public String key;



    
    public String owner;
    public long getEndToEndId() {
        return endToEndId;
    }

    public double value;
    public String getCPF() {
        return cpf.value;
    }

    public ResolvedStates resolved;
    public BankingDomicile getBankingDomicile() {
        return bankingDomicile;
    }
}
