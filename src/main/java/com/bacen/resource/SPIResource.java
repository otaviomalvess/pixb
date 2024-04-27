package com.bacen.resource;

import com.bacen.control.SPI;
import com.bacen.model.Pix;
import com.bacen.model.PixRequestUpdateDTO;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/bacen/spi/request")
public class SPIResource {

    @Inject
    private SPI spi;

    @POST
    public Response createRequest(final Pix[] pixes) {
        try {
            spi.createRequests(pixes);
            return Response.ok().build();
        } catch (final Exception e) {
            return Response.serverError().build();
        }
    }
    
    @GET
    public Response consultRequests() {
        try {
            return Response
                    .ok()
                    .entity(spi.getRequests(true))
                    .build();
        } catch (final Exception e) {
            return Response.serverError().build();
        }
    }

    @PUT
    public Response updateRequest(final PixRequestUpdateDTO[] toUpdate) {
        try {
            spi.updateRequests(toUpdate);
            return Response.ok().build();
        } catch (final Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/state")
    public Response consultUpdatedPixes() {
        try {
            return Response
                    .ok(spi.consultUpdatedPixes())
                    .build();
        } catch (final Exception e) {
            return Response.serverError().build();
        }
    }
}