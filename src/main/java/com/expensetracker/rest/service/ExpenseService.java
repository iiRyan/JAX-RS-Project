package com.expensetracker.rest.service;

import java.util.*;

import org.bson.Document;

import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;

import com.expensetracker.rest.db.MongoDBClient;
import com.expensetracker.rest.exception.MissingFieldException;
import com.expensetracker.rest.model.Expense;
import com.expensetracker.rest.model.ObjectIdDeserializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

/*
* The Service layer’s single responsibility is to do any logic required with
the data received by the Resource.
*/
public class ExpenseService {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ExpenseService.class);

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public ExpenseService() {
        mongoClient = MongoDBClient.INSTANCE.getMongoClient();
        database = mongoClient.getDatabase("myExpenses");
        collection = database.getCollection("expenses");
        LOGGER.info("initialized MongoDB Client DB name {}" + database.getName());

    }

    public Expense insertExpense(Expense expense) {
        if (expense.getTitle() == null || expense.getTitle().isEmpty()) {
            throw new MissingFieldException("Title is missing or null");
        }
        if (expense.getCategory() == null || expense.getCategory().isEmpty()) {
            throw new MissingFieldException("Category is missing or null");
        }

        Document doc = new Document("title", expense.getTitle())
                .append("amount", expense.getAmount())
                .append("category", expense.getCategory());
        collection.insertOne(doc);
        LOGGER.info("Expense inserted!{}" + expense);

        return expense;
    }

    public List<Expense> getExpenses() {
        LOGGER.info("fetching expenses...");
        FindIterable<Document> expenses = collection.find();
        List<Expense> expenseList = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule mod = new SimpleModule("ObjectId", new Version(1, 0, 0, null, null, null));
        mod.addDeserializer(ObjectId.class, new ObjectIdDeserializer());
        mapper.registerModule(mod);

        for (Document document : expenses) {
            try {
                Expense obj = mapper.readValue(document.toJson(), Expense.class);
                expenseList.add(obj);
            } catch (JsonProcessingException e) {
                LOGGER.error("Error occurred when parsing the object");
                e.printStackTrace();
            }
        }
        return expenseList;
    }

    public void deleteExpense(String id) {

        try {
            DeleteResult result = collection.deleteOne(new Document("_id", new ObjectId(id)));
            LOGGER.info("Deleted document count: " + result.getDeletedCount());
        } catch (MongoException me) {
            LOGGER.error("Unable to delete due to an error: {}" + me);
        }
    }

    public static void main(String[] args) {
        ExpenseService service = new ExpenseService();

        service.getExpenses();
    }
}
