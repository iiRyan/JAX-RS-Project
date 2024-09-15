package com.expensetracker.rest.resources;

import com.expensetracker.rest.model.Month;
import com.expensetracker.rest.service.MonthService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/months")
public class MonthResource {
    MonthService service = new MonthService();

    @Path("{month}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(@PathParam("month") String theMonth) {
        Month month = service.findAll(theMonth);
        return Response.status(Response.Status.OK)
        .entity(month)
        .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertMonth(Month theMonth){
        Month month = service.insertMonth(theMonth);
        return Response.status(Status.CREATED)
        .entity(month)
        .build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteMonth(@PathParam("id")String id){
       boolean isDeleted = service.deleteMonth(id);
        if(isDeleted){
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();    
    }
}
