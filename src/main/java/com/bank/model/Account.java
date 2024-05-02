package com.bank.model;

import com.util.common.IBankingDomicile;
import com.util.common.ICPF;
import com.util.model.BankingDomicile;
import com.util.model.CPF;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account extends PanacheEntityBase implements ICPF, IBankingDomicile {
    
    @Column(name = "name")
    public String name;
    
    @Column(name = "balance")
    private double balance;
    
    @Embedded
    private BankingDomicile bankingDomicile;

    // @Embedded
    @EmbeddedId
    private CPF cpf;

    /**
     * 
     */
    public Account() {}
    
    public Account(final AccountRegisterDTO accountDTO) {
        this(accountDTO.name, accountDTO.cpf, accountDTO.branch);
    }

    public Account(final String name, final String cpf, final int branch) {
        if (name == null)
            throw new NullPointerException("Name cannot be null");
        if (name.isBlank())
            throw new IllegalArgumentException("Name cannot be blank");
        
        this.name = name;
        this.bankingDomicile = new BankingDomicile(branch);
        this.cpf = new CPF(cpf);
    }

    /**
     * Returns the account balance.
     * 
     * @return The account balance value.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Deposits the given amount to the account.
     *
     * @param value The amount to deposit.
     * @throws IllegalArgumentException If the given value is not greater than .0d.
     */
    public void deposit(final double value) {
        if (value <= .0)
            throw new IllegalArgumentException("Value cannot be equal or smaller than .0d");

        balance += value;
    }

    /**
     * Draws the given amount from the account.
     *
     * @param value The amount to draw.
     * @throws IllegalArgumentException If the given value is not greater than .0d
     * @throws Exception If the account doesn't have enough balance.
     */
    public void draw(final double value) throws Exception {
        if (value <= .0)
            throw new IllegalArgumentException("Value cannot be equal or smaller than .0d");
        if (balance - value < .0)
            throw new Exception("Not enough balance");

        balance -= value;
    }

    public String getCPF() {
        return cpf.value;
    }

    public BankingDomicile getBankingDomicile() {
        return bankingDomicile;
    }

    /**
     * Hibernate
     */
    private void setCPF(final CPF cpf) {
        this.cpf = cpf;
    }
}
