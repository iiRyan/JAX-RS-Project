package com.expensetracker.rest.model;

import java.io.IOException;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ObjectIdDeserializer extends JsonDeserializer<ObjectId> {

    
     @Override
    public ObjectId deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        
        // Check if the node is an object and extract the $oid field
        if (node.has("$oid")) {
            String objectIdStr = node.get("$oid").asText();
            return new ObjectId(objectIdStr);
        }
        
        return null;  // Return null if no valid ObjectId found
    }
}