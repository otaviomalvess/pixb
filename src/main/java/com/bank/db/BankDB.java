package com.bank.db;

import org.jboss.logging.Logger;

import com.bank.common.IAccountDB;
import com.bank.model.Account;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

/**
 * BankDB is a class handling any database operations related to the bank.
 */
@Singleton
public class BankDB implements IAccountDB {

    @Inject
    private Logger logger;

    public void deleteAccount(final String cpf) {
        final boolean success = Account.deleteById(cpf);
        if (!success) {
            logger.info("(deleteAccount) Entity not found.");
        }
    }

    public Account getAccount(final String cpf) {
        try {
            return Account.findById(cpf);
        } catch (final Exception e) {
            logger.error("(getAccount) " + e);
            return null;
        }
    }

    @Transactional
    public void insertAccount(final Account account) {
        try {
            account.persist();
        } catch (final Exception e) {
            logger.error("(insertAccount) " + e);
        }
    }

    @Transactional
    public Exception updateAccountBalance(final Account account) {
        Exception ex = null;

        try {
            Account.update("balance", account.balance);
        } catch (final Exception e) {
            logger.error("(updateAccountBalance) " + e);
            ex = new Exception("");
        }

        return ex;
    }
}
