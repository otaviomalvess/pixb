package com.bacen.model;

import com.bacen.model.Pix.ResolvedStates;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PixRequestUpdateDTO is a class containing the details of the resolution state of a pix request to update.
 */
public class PixRequestUpdateDTO {
    
    public final ResolvedStates resolved;
    
    @JsonProperty(value = "end_to_end_id")
    public final long endToEndId;

    public PixRequestUpdateDTO(final ResolvedStates resolved, final long endToEndId) {
        this.resolved = resolved;
        this.endToEndId = endToEndId;
    }
}
