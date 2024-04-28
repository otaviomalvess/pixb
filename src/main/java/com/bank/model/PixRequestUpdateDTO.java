package com.bank.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PixRequestUpdateDTO is a class containing the details of the resolution state of a pix request to update.
 */
public class PixRequestUpdateDTO {
    
    @JsonProperty(value = "end_to_end_id")
    public long endToEndId;
    public Pix.ResolvedStates resolved;

    public PixRequestUpdateDTO(long endToEndId) {
        this.endToEndId = endToEndId;
    }
}
