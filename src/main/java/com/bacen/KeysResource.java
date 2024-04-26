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
    public Response keysCheck(final String[] body) {
        try {
            return Response
                    .ok(dict.consultExistentKeys(body))
                    .build();
        } catch(final Exception e) {
            System.out.println(e);
            return Response.serverError().build();
        }
    }
}
