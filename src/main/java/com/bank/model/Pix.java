package com.bank.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pix {
    
    public enum ResolvedStates { REQUEST, FAIL, SUCCESS }

    @JsonProperty(value = "end_to_end_id")
    public long endToEndId;

    @JsonProperty(value = "pkey")
    public String key;

    public int bank;

    public int branch;

    public int account;
    
    public String cpf;
    
    public String owner;

    public double value;

    public ResolvedStates resolved;
}