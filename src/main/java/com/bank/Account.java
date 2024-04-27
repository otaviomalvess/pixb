package com.bank;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account extends PanacheEntityBase {
    
    @Id
    @Column(name = "cpf")
    public String cpf;
    
    @Column(name = "name")
    public String name;

    @Column(name = "bank")
    public int bank;

    @Column(name = "branch")
    public int branch;

    @Column(name = "account")
    public int account;

    @Column(name = "balance")
    public double balance;

    public Account () {}

    public Account(final AccountRegisterDTO accountDTO) {
        this(accountDTO.name, accountDTO.cpf);
    }

    public Account(final String name, final String cpf) {
        setName(name);
        setCPF(cpf);
    }

    /**
     * Set the account name.
     *
     * @param name the name of the account.
     */
    public void setName(final String name) {
        if (name == null)
            throw new NullPointerException("Name cannot be null");
        if (name.isBlank())
            throw new IllegalArgumentException("Name cannot be blank");

        this.name = name;
    }

    /**
     * Set the account cpf.
     *
     * @param cpf the cpf of the account.
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public void setCPF(final String cpf) {
        if (cpf == null)
            throw new NullPointerException("CPF cannot be null");
        if (cpf.isBlank())
            throw new IllegalArgumentException("CPF cannot be blank");
        if (cpf.length() != 11)
            throw new IllegalArgumentException("CPF length cannot be different of 11");

        this.cpf = cpf;
    }

    /**
     * Deposits the given amount to the balance.
     *
     * @param value the amount to add.
     */
    public void deposit(final double value) throws Exception {
        if (value <= .00) {
            throw new Exception("Given value has to be higher than 0.0");
        }

        balance += value;
    }

    /**
     * Draws the requested amount from the balance.
     *
     * @param value the amount to draw.
     * @return an {@code Exception} if the given value is smaller than 0.0 or if the account hasn't enough balance.
     */
    public void draw(final double value) throws Exception {
        if (value <= .0) {
            throw new Exception("Given value has to be higher than 0.0");
        }

        if (balance - value < .0) {
            throw new Exception("Not enough balance");
        }

        balance -= value;
    }
}
