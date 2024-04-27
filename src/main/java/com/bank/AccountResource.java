package com.bank;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pixb/account/{id}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {

    @POST
    public Response createAccount(final AccountRegisterDTO account) {
        try {
            accountControl.createAccount(account);
            return Response.ok().build();
        } catch (final Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{cpf}")
    public Response getAccountInfo(@PathParam("cpf") final String cpf) {
        try {
            return Response
                    .ok(accountControl.getAccount(cpf))
                    .build();
        } catch(final Exception e) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{cpf}/deposit")
    public Response deposit(final DepositDTO deposit, @PathParam("cpf") final String cpf) {
        try {
            accountControl.depositTo(cpf, deposit.value);
            return Response.ok().build();
        } catch (final Exception e) {
            return Response.serverError().build();
        }
    }
}
