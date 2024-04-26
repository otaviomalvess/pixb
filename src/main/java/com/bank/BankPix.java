package com.bank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BankPix {
    
    enum ResolvedStates { REQUEST, FAIL, SUCCESS }

    @JsonProperty(value = "end_to_end_id")
    public long endToEndId;

    public int bank;

    public int branch;

    public int account;
    
    public String cpf;
    
    public String owner;

    public double value;

    public ResolvedStates resolved;
}
