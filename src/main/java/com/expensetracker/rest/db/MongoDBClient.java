package com.expensetracker.rest.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public enum MongoDBClient {

    INSTANCE;

    public MongoClient getMongoClient() {
        Logger LOGGER = LogManager.getLogger(MongoDBClient.class);
        try {
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            return mongoClient;
        } catch (MongoException e) {
            LOGGER.error("Connection to MongoDB was filed due to {}" + e);
        }
        return null;
    }

}
