package com.expensetracker.rest.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.bind.JsonbException;

// this annotation register this mapper so it can handle exceptions across your application
@Provider
// ExceptionMapper for JsonbException
public class JsonbExceptionMapper implements ExceptionMapper<JsonbException> {

    @Override
    public Response toResponse(JsonbException exception) {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("message", "Invalid input provided. Please check the data format.")
                .build();

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(jsonObject.toString())
                .type("application/json")
                .build();
    }
}
