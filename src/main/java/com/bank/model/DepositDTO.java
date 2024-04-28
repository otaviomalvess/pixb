package com.bank.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DepositDTO is a simple DTO class containing the value of a deposit.
 */
public class DepositDTO {
    
    @JsonProperty
    public double value;
}
