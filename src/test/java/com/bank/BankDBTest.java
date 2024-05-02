package com.bank;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.bank.db.BankDB;
import com.bank.model.Account;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class BankDBTest {
    
    @Inject
    private BankDB db;

    @Test
    private void testGetAccount() {
        final Account account = db.getAccount("22222222201");

        assertNotNull(account);
    }

    @Test
    private void createAccount() {
        final String cpf = "33333333301";
        final Account account = new Account("Carlos", cpf, 0);

        db.insertAccount(account);

        final Account retAccount = db.getAccount(cpf);

        assertNotNull(retAccount);
    }

    @Test
    private void testUpdateAccountBalance() {
        final String cpf = "33333333301";
        final Account account = db.getAccount(cpf);

        account.deposit(200.0);

        db.updateAccountBalance(account);
    }

    @Test
    private void testDeleteAccount() {
        final String cpf = "33333333301";
        db.deleteAccount(cpf);
    }
}
