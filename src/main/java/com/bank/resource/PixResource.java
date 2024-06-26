package com.bank.resource;

import com.bank.control.PixControl;
import com.bank.model.PixDTO;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * PixResource is a class containing all pix related endpoints.
 */
@Consumes(MediaType.APPLICATION_JSON)
@Path("/bank/pix")
public class PixResource {

    @Inject
    private PixControl bankPixControl;

    @GET
    @Path("/{key}")
    public Response consultEntry(@PathParam("key") final String key) {
        try {
            // return pixService.consultKey(key)
            return Response.ok(key).build();
        } catch (final Exception e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/create-pix-request")
    public Response createPixRequests(final PixDTO[] startPix) {
        try {
            bankPixControl.createPixRequests(startPix);
            return Response.ok().build();
        } catch (final Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/consult-pix-requests")
    public Response consultPixRequests() {
        try {
            bankPixControl.consultPixRequests();
            return Response.ok().build();
        } catch (final Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/consult-updated-pixes")
    public Response consultPixUpdates() {
        try {
            bankPixControl.consultUpdatedPixes();
            return Response.ok().build();
        } catch (final Exception e) {
            return Response.serverError().build();
        }
    }
}
