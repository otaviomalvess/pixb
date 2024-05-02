package com.bank.common;

import com.bank.model.Account;

public interface IAccountDB {
    
    /**
     * Deletes an account.
     * 
     * @param cpf The CPF associated to the account.
     */
    public void deleteAccount(String cpf);

    /**
     * Returns an account.
     *
     * @param cpf The CPF associated to the account.
     * @return The found account.
     */
    public Account getAccount(String cpf);

    /**
     * Persists a new account.
     *
     * @param account The account to persist. 
     */
    public void insertAccount(Account account);

    /**
     * Updates the account balance.
     *
     * @param account The account to update.
     * @return an Exception if the operation didn't work.
     */
    public void updateAccountBalance(Account account);
}
