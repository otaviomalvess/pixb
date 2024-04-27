package com.bank.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BankPixRequestUpdateDTO {
    
    @JsonProperty(value = "end_to_end_id")
    public long endToEndId;
    public BankPix.ResolvedStates resolved;

    public BankPixRequestUpdateDTO(long endToEndId) {
        this.endToEndId = endToEndId;
    }
}
