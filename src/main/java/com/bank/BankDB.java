package com.bank;

public class BankDB {

    /**
     * Gets the account from the DB.
     *
     * @param cpf the account tax number.
     * @return an {@code Account}.
     */
    public static Account getAccount(final String cpf) {
        try {
            return Account.findById(cpf);
        } catch (Exception e) {
            System.out.println("[ERROR] (com.bank.BankDB) Find account by id: cpf.");
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
            Account.persist(a);
        } catch (Exception e) {
            System.out.println("[ERROR] (com.bank.BankDB) " + e.getMessage());
            ex = new Exception("");
        }

        return ex;
    }
}
