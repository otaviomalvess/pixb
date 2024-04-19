package com.bacen;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BacenPixRequestUpdateDTO {
    
    @JsonProperty(value = "end_to_end_id")
    public String endToEndId;

    public BacenPix.ResolvedStates resolved;
}
