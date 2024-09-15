package com.expensetracker.rest.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.expensetracker.rest.service.ExpenseService;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public enum MongoDBClient {

    INSTANCE;

    public MongoClient getMongoClient() {
        final Logger LOGGER = LoggerFactory.getLogger(ExpenseService.class);

        try {
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            return mongoClient;
        } catch (MongoException e) {
            LOGGER.error("Connection to MongoDB was filed due to {}" + e);
        }
        return null;
    }

}
