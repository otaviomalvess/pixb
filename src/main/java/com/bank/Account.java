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

    public void setName(String name) {
        if (name.isBlank()) {
            return;
        }

        this.name = name;
    }

    public Exception deposit(double value) {
        if (value <= .00) {
            return new Exception("Given value has to be higher than 0.0.");
        }

        balance += value;
        return null;
    }

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
