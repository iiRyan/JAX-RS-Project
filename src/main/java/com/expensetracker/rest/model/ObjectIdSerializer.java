package com.expensetracker.rest.model;

import java.io.IOException;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.JsonSerializer;

public class ObjectIdSerializer extends JsonSerializer<ObjectId>{
    
    @Override
    public void serialize(ObjectId objectId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (objectId != null) {
            jsonGenerator.writeString(objectId.toHexString());  // Convert ObjectId to its hex string representation
        } else {
            jsonGenerator.writeNull();
        }
    }
    
}
