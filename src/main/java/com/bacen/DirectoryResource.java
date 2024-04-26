package com.bacen;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/api/entry")
public class DirectoryResource {

    @Inject
    private Dict dict;

    @GET
    @Path("{key}")
    public Response cosultEntry(@PathParam("key") String key) {
        try {
            return Response
                    .ok(dict.consultEntry(key))
                    .build();
        } catch (final Exception e) {
            System.out.println(e);
            return Response.serverError().build();
        }
    }

}
