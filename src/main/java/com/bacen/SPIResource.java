package com.bacen;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/bacen/spi/request")
public class SPIResource {

    @POST
    public Response saveRequest(BacenPix pix) {
        try {
            SPI.createPixRequest(pix);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
    
    @GET
    public Response consultRequest() {
        try {
            return Response
                    .ok()
                    .entity(SPI.consultRequest())
                    .build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @PUT
    public Response updateRequest(BacenPixRequestUpdateDTO[] toUpdate) {
        try {
            SPI.closeRequests(toUpdate);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}