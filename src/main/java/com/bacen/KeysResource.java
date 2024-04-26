package com.bacen;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/api/keys")
public class KeysResource {
    
    @Inject
    private Dict dict;

    @POST
    public Response keysCheck(String[] body) {
        try {
            return Response
                    .ok(Dict.consultExistentKeys(body))
                    .build();
        } catch(Exception e) {
            logger.debug(e.getMessage());
            return Response.serverError().build();
        }
    }
}
