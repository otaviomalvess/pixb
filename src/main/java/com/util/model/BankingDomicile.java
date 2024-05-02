package com.util.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class BankingDomicile {
    
    public static final int BANK_CODE = 0;
    
    @Column(name = "branch")
    public final int branch;
    
    @Column(name = "bank")
    private int bank;
    
    @Column(name = "account")
    private int account;

    /**
     * 
     */
    public BankingDomicile(final int branch) {
        this.branch = branch;
    }

    public BankingDomicile(final int bank, final int branch, final int account) {
        this.bank = bank;
        this.branch = branch;
        this.account = account;
    }

    /**
     * Returns the bank value.
     * 
     * @return The bank value.
     */
    public int getBank() {
        return bank;
    }

    /**
     * Returns the branch value.
     * 
     * @return The branch value.
     */
    public int getBranch() {
        return branch;
    }

    /**
     * Returns the account value.
     * 
     * @return The account value.
     */
    public int getAccount() {
        return account;
    }
}
