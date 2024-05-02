package com.util.model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
@AttributeOverride(name = "value", column = @Column(name = "cd_cpf"))
public class CPF {
    
    @Access(AccessType.FIELD)
    public final String value;

    public CPF(final String value) {
        super();
        
        if (value == null)
            throw new NullPointerException("Value cannot be null");
        if (value.isBlank())
            throw new IllegalArgumentException("Value cannot be blank");
        if (value.length() != 11)
            throw new IllegalArgumentException("Value length cannot be different from 11");

        this.value = value;
    }
}
