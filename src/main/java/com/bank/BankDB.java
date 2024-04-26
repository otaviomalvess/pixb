package com.bank;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class BankDB {

    @Inject
    private Logger logger;

    /**
     * Gets the account from the DB.
     *
     * @param cpf the account tax number.
     * @return an {@code Account}.
     */
    public static Account getAccount(final String cpf) {
        try {
            return Account.findById(cpf);
        } catch (final Exception e) {
            logger.error("(getAccount) " + e);
            return null;
        }
    }

    /**
     * Updates the account balance.
     *
     * @param account the account to update.
     * @return an {@code Exception} if the operation didn't work.
     */
    public static Exception updateAccountBalance(final Account a) {
        Exception ex = null;

        try {
            account.persist();
        } catch (final Exception e) {
            logger.error("(updateAccountBalance) " + e);
            ex = new Exception("");
        }

        return ex;
    }
}
