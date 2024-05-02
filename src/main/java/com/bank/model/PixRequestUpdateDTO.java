package com.bank.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PixRequestUpdateDTO is a class containing the details of the resolution state of a pix request to update.
 */
public class PixRequestUpdateDTO {
    
    public Pix.ResolvedStates resolved;
    
    @JsonProperty(value = "end_to_end_id")
    private long endToEndId;

    public PixRequestUpdateDTO(final long endToEndId) {
        this.endToEndId = endToEndId;
    }

    /**
     * Returns the pix request end to end ID.
     * 
     * @return The end to end ID.
     */
    public long getEndToEndId() {
        return endToEndId;
    }
}
