package com.bank.control;

import org.jboss.logging.Logger;

import com.bank.db.BankDB;
import com.bank.model.Account;
import com.bank.model.AccountRegisterDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * AccountControl is a class managing any account related processes.
 */
@ApplicationScoped
public class AccountControl {
    
    @Inject
    private BankDB db;

    @Inject
    private Logger logger;

    /**
     * Create a new account.
     * 
     * @param accountDTO The account DTO object
     * @see AccountRegisterDTO
     */
    public void createAccount(final AccountRegisterDTO accountDTO) {
        final Account account = new Account(accountDTO);
        db.insertAccount(account);
    }

    /**
     * Deposits to an account.
     * 
     * @param cpf The CPF associated to the account
     * @param value The deposit value
     */
    public void depositTo(final String cpf, final double value) {
        updateBalance(false, getAccount(cpf), value);
    }

    /**
     * Deposits to an account.
     * 
     * @param account The account
     * @param value The deposit value
     */
    public void depositTo(final Account account, final double value) {
        updateBalance(false, account, value);
    }

    /**
     * Draws from an account.
     * 
     * @param cpf The CPF associated to the account
     * @param value The draw value
     */
    public void drawFrom(final String cpf, final double value) {
        updateBalance(false, getAccount(cpf), value);
    }
    
    /**
     * Get an account.
     * 
     * @param cpf The CPF associated to the account
     * @return The account
     * @see Account
     */
    public Account getAccount(final String cpf) {
        if (cpf.isBlank()) {
            logger.error("(getAccount) Given CPF is blank.");
            return null;
        }

    /**
     * Deposits to or draws from an account.
     * 
     * @param toDeposit 
     * @param cpf The CPF associated to the account
     * @param value The draw value
     * @see Account
     */
    private void updateBalance(final boolean toDeposit, final Account account, final double value) {
        try {
            if (toDeposit) {
                account.deposit(value);
            } else {
                account.draw(value);
            }

        return db.getAccount(cpf);
            db.updateAccountBalance(account);
        } catch (final Exception e) {
            logger.error("(depositTo) " + e);
            throw new RuntimeException();
        }
    }
}
