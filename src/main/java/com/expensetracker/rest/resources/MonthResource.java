package com.expensetracker.rest.resources;

import com.expensetracker.rest.model.Month;
import com.expensetracker.rest.service.MonthService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/budgets")
public class MonthResource {
    MonthService service = new MonthService();

    @Path("{month}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Month findAll(@PathParam("month") String month) {
        System.out.println("Enter the get method " + month);
        Month monthFinancial = service.findAll(month);

        return monthFinancial;
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
}
