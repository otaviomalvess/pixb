package com.bank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.util.model.BankingDomicile;
import com.util.model.CPF;

/**
 * Pix is a class containing the details and state of a pix transfer request.
 */
public class Pix {
    
    public enum ResolvedStates { REQUEST, FAIL, SUCCESS }

    @JsonProperty(value = "end_to_end_id")
    public long endToEndId;
    
    private BankingDomicile bankingDomicile;
    private CPF cpf;

    @JsonProperty(value = "pkey")
    public String key;



    
    public String owner;

    public double value;

    public ResolvedStates resolved;
}
