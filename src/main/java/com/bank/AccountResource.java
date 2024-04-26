package com.bank;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pixb/account/{id}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {

    @GET
    public Response accountInfo(@PathParam("id") Long id) {
        try {
            return Response.ok().build();
        } catch(final Exception e) {
            System.out.println(e);
            return Response.serverError().build();
        }
    }
}
