package com.bank.service;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.Separator;

import com.bank.model.Pix;
import com.bank.model.PixRequestUpdateDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(configKey = "bank-pix-service")
@ApplicationScoped
public interface BankPixService {
    
    @GET
    @Path("/bacen/dict/entry")
    public Response getEntries(@QueryParam("keys") @Separator(",") String[] keys);

    @POST
    @Path("/bacen/spi/request")
    public Response createPixRequests(Pix[] pix);

    @GET
    @Path("/bacen/spi/request")
    public Response consultPixRequests();

    @GET
    @Path("/bacen/spi/request/state")
    public Response consultUpdatedPixes();

    @PUT
    @Path("/bacen/spi/request")
    public Response closePixRequests(PixRequestUpdateDTO[] resolvedPixes);
}
