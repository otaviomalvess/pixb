package com.bacen;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Path("/bacen/spi/request")
public class SPIResource {

    @Inject
    private SPI spi;

    @POST
    public Response saveRequest(final BacenPix pix) {
        try {
            spi.createPixRequest(pix);
            return Response.ok().build();
        } catch (final Exception e) {
            return Response.serverError().build();
        }
    }
    
    @GET
    public Response consultRequest() {
        try {
            return Response
                    .ok()
                    .entity(spi.getPixRequests(true))
                    .build();
        } catch (final Exception e) {
            return Response.serverError().build();
        }
    }

    @PUT
    public Response updateRequest(final BacenPixRequestUpdateDTO[] toUpdate) {
        try {
            spi.updatePixRequests(toUpdate);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}