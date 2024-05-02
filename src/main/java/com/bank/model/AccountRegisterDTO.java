package com.bank.model;

/**
 * AccountRegisterDTO is a simple DTO class containing the user data for a new account registration request.
 * <p>
 * Can be used as a constructor parameter of the Account class.
 */
public class AccountRegisterDTO {
    public final String cpf;
    public final String name;
    public final int branch;

    public AccountRegisterDTO(final String cpf, final String name, final int branch) {
        this.cpf = cpf;
        this.name = name;
        this.branch = branch;
    }
}
