package com.bank.model;

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
     * Sets the user name.
     *
     * @param name The name of the user.
     * @throws NullPointerException If the given name is null.
     * @throws IllegalArgumentException If the given name is blank.
     */
    public void setName(final String name) {
        if (name == null)
            throw new NullPointerException("Name cannot be null");
        if (name.isBlank())
            throw new IllegalArgumentException("Name cannot be blank");

        this.name = name;
    }

    /**
     * Sets the account CPF.
     *
     * @param cpf The account CPF.
     * @throws NullPointerException If the given CPF is null.
     * @throws IllegalArgumentException If the given CPF is blank.
     * @throws IllegalArgumentException If the given CPF length is not equal to 11.
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
     * Deposits the given amount to the account.
     *
     * @param value The amount to deposit.
     * @throws Exception If the given value is not greater than .0d.
     */
    public void deposit(final double value) throws Exception {
        if (value <= .00) {
            throw new Exception("Given value has to be higher than 0.0");
        }

        balance += value;
    }

    /**
     * Draws the given amount from the account.
     *
     * @param value The amount to draw.
     * @throws Exception If the given value is not greater than .0d.
     * @throws Exception If the account doesn't have enough balance.
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
