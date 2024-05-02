package com.bank.model;

/**
 * PixRollbacker is a class containing the details of a drawn operation associated to an account.
 */
public class PixRollbacker {
    
    public final Account account;
    public final double value;

    public PixRollbacker(final Account account, final double value) {
        this.account = account;
        this.value = value;
    }
}
