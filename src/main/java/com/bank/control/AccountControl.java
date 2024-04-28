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
     * Make a deposit to an account.
     * 
     * @param cpf The CPF associated to the account
     * @param value The deposit value
     */
    public void depositTo(final String cpf, final double value) {
        final Account account = getAccount(cpf);
        if (account == null) {
            logger.error("No account found.");
            return;
        }

        try {
            account.deposit(value);
            db.updateAccountBalance(account);
        } catch (final Exception e) {
            logger.error("(depositTo)" + e);
        }
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

        return db.getAccount(cpf);
    }
}
