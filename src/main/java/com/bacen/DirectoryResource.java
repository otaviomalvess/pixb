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
            BacenEntry e = Dict.consultEntry(key);
            logger.info(e.toString());
            return Response
                    .ok(e)
                    .build();
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return Response.serverError().build();
        }
    }

}
