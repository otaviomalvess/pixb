package com.bacen.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PixRequestUpdateDTO {
    
    @JsonProperty(value = "end_to_end_id")
    public long endToEndId;

    public Pix.ResolvedStates resolved;
}
