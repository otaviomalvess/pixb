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

    // @POST
    // @Transactional
    // public Response pix(LinkedHashMap<String, String> json) {
    //     try {
    //         Pix p = new ObjectMapper().convertValue(json, Pix.class);
    //         Account from = Account.findById(p.accountFrom);
    //         Account to = Account.findById(p.accountTo);

    //         if (from.balance - p.value < 0.0) {
    //             return Response.ok("Senter doesn't have enough balance.").build();
    //         }
            
    //         from.balance -= p.value;
    //         to.balance += p.value;
    //         p.date = new Date();

    //         from.persist();
    //         to.persist();
    //         p.persist();
            
    //         return Response.ok().build();

    //     } catch (Exception e) {
    //         return Response.serverError().build();
    //     }
    // }
}
