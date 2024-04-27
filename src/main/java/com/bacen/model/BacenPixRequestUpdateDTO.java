package com.bacen.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BacenPixRequestUpdateDTO {
    
    @JsonProperty(value = "end_to_end_id")
    public long endToEndId;

    public BacenPix.ResolvedStates resolved;
}
