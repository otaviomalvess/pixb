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

    /**
     * Set the account name.
     *
     * @param name the name of the account.
     */
    public void setName(String name) {
        if (name.isBlank()) {
            return;
        }

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
    public Exception deposit(double value) {
        if (value <= .00) {
            return new Exception("Given value has to be higher than 0.0.");
        }

        balance += value;
        return null;
    }

    /**
     * Draws the requested amount from the balance.
     *
     * @param value the amount to draw.
     * @return an {@code Exception} if the given value is smaller than 0.0 or if the account hasn't enough balance.
     */
    public Exception draw(double value) {
        if (value <= .0) {
            return new Exception("Given value has to be higher than 0.0.");
        }

        if (balance - value < .0) {
            return new Exception("Not enough balance.");
        }

        balance -= value;
        return null;
    }
}
