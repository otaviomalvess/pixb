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
        return Account.findById(cpf);
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
    public void updateAccountBalance(final Account account) {
        final int updates = Account.update("balance = ?1 WHERE cpf = ?2", account.getBalance(), account.getCPF());
        if (updates == 0) {
            logger.warn("(updateAccountBalance) 0 entities updated.");
        } else if (updates > 1) {
            logger.error("(updateAccountBalance) %d entities updated".formatted(updates));
        }
    }
}
