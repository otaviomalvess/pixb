package com.bank;

public class BankDB {

    /**
     * Get account.
     *
     * @param cpf .
     * @return .
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
     * Update account balance.
     *
     * @param id .
     * @return .
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
