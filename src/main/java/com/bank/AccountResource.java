package com.bank;

import org.jboss.logging.Logger;

import jakarta.inject.Inject;

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

    @Inject
    private Logger logger;

    @GET
    public Response accountInfo(@PathParam("id") Long id) {
        try {
            return Response
                    .ok()
                    .build();
        } catch(Exception e) {
            logger.debug(e.getMessage());
            return Response.serverError().build();
        }
    }
}
