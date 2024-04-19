package com.bank;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(configKey = "bank-pix-service")
public interface BankPixService {
    
    @GET
    @Path("/bacen/dict/entry/{key}")
    public Response consultKey(@PathParam("key") String key);

    @POST
    @Path("/bacen/spi/request")
    public Response pixRequest(BankPix pix);

    @GET
    @Path("/bacen/spi/request")
    public Response consultPixRequest();

    @PUT
    @Path("/bacen/spi/request")
    public Response closePixRequests(BankPixRequestUpdateDTO[] resolvedPixes);
}
