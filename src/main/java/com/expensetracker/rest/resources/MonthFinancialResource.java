package com.expensetracker.rest.resources;


import com.expensetracker.rest.model.MonthFinancial;
import com.expensetracker.rest.service.MonthFinancialService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/budgets")
public class MonthFinancialResource {
    MonthFinancialService service = new MonthFinancialService();


    @Path("{month}")  // Specify the path parameter in the URL
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MonthFinancial findAll(@PathParam("month") String month){
        System.out.println("Enter the get method " + month);
        MonthFinancial monthFinancial = service.findAll(month);

        return monthFinancial;
    }
}
