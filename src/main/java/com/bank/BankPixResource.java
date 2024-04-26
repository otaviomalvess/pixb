package com.bank;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("/pixb/pix")
public class BankPixResource {

    @Inject
    private BankPixControl bankPixControl;

    @GET
    @Path("/{key}")
    public Response consultEntry(@PathParam("key") String key) {
        try {
            return pixService.consultKey(key);
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/start")
    public Response start(final BankStartPixDTO startPix) {
        try {
            BankPixControl.createPixRequest(startPix, pixService);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/end")
    public Response end() {
        try {
            BankPixControl.consultPixRequest(pixService);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}
