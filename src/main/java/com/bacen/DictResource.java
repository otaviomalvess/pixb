package com.bacen;

import org.jboss.resteasy.reactive.Separator;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/bacen/dict")
public class DictResource {
    
    @Inject
    private Dict dict;

    @GET
    @Path("/entry")
    public Response getEntries(@QueryParam("keys") @Separator(",") final String[] keys) {
        try {
            return Response
                    .ok()
                    .entity(dict.getEntries(keys))
                    .build();
        } catch (final Exception e) {
            return Response.serverError().build();
        }
    }
}