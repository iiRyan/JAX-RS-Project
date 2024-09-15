package com.expensetracker.rest.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@Provider
public class MissingFieldExceptionMapper implements ExceptionMapper<MissingFieldException> {

    @Override
    public Response toResponse(MissingFieldException exception) {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("message", exception.getMessage())
                .build();

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(jsonObject.toString())
                .type("application/json")
                .build();
    }
}