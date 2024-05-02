package com.util.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class PixKey {
    
    public enum Types { CPF, EMAIL, PHONE }
    
    @Column(name = "key")
    public final String key;
    
    @Column(name = "key_type")
    @Enumerated(EnumType.ORDINAL)
    @JsonProperty(value = "key_type")
    public final Types type;

    /**
     * 
     */
    public PixKey(final String key, final Types type) {
        if (key == null)
            throw new NullPointerException("Key cannot be null");
        if (key.isBlank())
            throw new IllegalArgumentException("Key cannot be blank");
        
        switch (type) {
            case Types.CPF:
                new CPF(key);
            
            case Types.EMAIL:
                if (!key.contains("@"))
                    throw new IllegalArgumentException("Email key is invalid");
            
            case Types.PHONE:
                if (key.length() == 15)
                    throw new IllegalArgumentException("Phone key length can't be different from 15");
        }
        
        this.key = key;
        this.type = type;
    }
}
