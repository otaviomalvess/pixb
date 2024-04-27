package com.bank;

import java.util.ArrayList;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AccountControl {
    
    @Inject
    private BankDB db;

    @Inject
    private Logger logger;

    /**
     * Creates a new accounts.
     * 
     * @param accountDTO
     */
    public void createAccount(final AccountRegisterDTO accountDTO) {
        final Account account = new Account(accountDTO);
        db.insertAccount(account);
    }

    /**
     * Deposit to.
     * 
     * @param cpf
     * @param value
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
     * @param cpf
     * @return Account
     */
    public Account getAccount(final String cpf) {
        if (cpf.isBlank()) {
            logger.error("(getAccount) Given CPF is blank.");
            return null;
        }

        return db.getAccount(cpf);
    }

    /**
     * @param pixes
     * @return Account[]
     */
    public Account[] getAccounts(final BankPix[] pixes) {
        if (pixes == null || pixes.length == 0) {
            logger.error("(getAccounts) Given CPFs array is null or empty.");
            return null;
        }

        final ArrayList<Account> accounts = new ArrayList<>();
        for (final BankPix pix : pixes) {
            accounts.add(getAccount(pix.cpf));
        }

        return accounts.toArray(new Account[accounts.size()]);
    }
}
