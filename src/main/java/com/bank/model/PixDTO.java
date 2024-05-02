package com.bank.model;

/**
 * PixDTO is a class containing the details of a pix request made by the bank user.
 */
public class PixDTO {
    public final String key;
    public final double value;

    public PixDTO(final String pixKey, final double value) {
        this.key = pixKey;
        this.value = value;
    }
}
