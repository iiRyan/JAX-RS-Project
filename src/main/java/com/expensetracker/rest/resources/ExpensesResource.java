package com.expensetracker.rest.resources;

import java.util.List;

import com.expensetracker.rest.model.Expense;
import com.expensetracker.rest.service.ExpenseService;
import com.expensetracker.rest.service.MonthService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/expenses")
public class ExpensesResource {

    MonthService service = new MonthService();
    

    // @GET
    // @Produces(MediaType.APPLICATION_JSON)
    // public List<Expense> getExpenses() {
    //     return service.getExpenses();
    // }

    @POST
    @Path("{month}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertExpense(@PathParam("month") String theMonth,Expense expense) {
        Expense theExpense = service.insertExpense(theMonth,expense);
        return Response.status(Status.CREATED)
                .entity(theExpense)
                .build();
    }

    // @OPTIONS
    // @Path("/{id}")
    // public Response handlePreflight(@PathParam("id") String id) {
    //     return Response.ok()
    //             .header("Access-Control-Allow-Origin", "http://localhost:3000")
    //             .header("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS")
    //             .header("Access-Control-Allow-Headers", "Content-Type, Accept")
    //             .header("Access-Control-Allow-Credentials", "true")
    //             .build();
    // }

    // @DELETE
    // @Consumes(MediaType.APPLICATION_JSON)
    // @Produces(MediaType.APPLICATION_JSON)
    // @Path("/{id}")
    // public Response deleteExpense(@PathParam("id") String _id) {
    //     service.deleteExpense(_id);
    //     return Response.status(Response.Status.OK).build();
    // }

}
