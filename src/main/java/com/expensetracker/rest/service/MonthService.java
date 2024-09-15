package com.expensetracker.rest.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.expensetracker.rest.db.MongoDBClient;
import com.expensetracker.rest.model.Expense;
import com.expensetracker.rest.model.Income;
import com.expensetracker.rest.model.Month;
import com.expensetracker.rest.model.ObjectIdDeserializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

public class MonthService {
    private static final Logger LOGGER = LogManager.getLogger(MonthService.class);

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private ObjectMapper mapper;

    public MonthService() {

        mongoClient = MongoDBClient.INSTANCE.getMongoClient();
        database = mongoClient.getDatabase("myExpenses");
        collection = database.getCollection("monthlyBudget");

        mapper = new ObjectMapper();
        // Register the custom deserializer for ObjectId
        SimpleModule mod = new SimpleModule("ObjectIdModule", new Version(1, 0, 0, null, null, null));
        mod.addDeserializer(ObjectId.class, new ObjectIdDeserializer()); // Add custom deserializer
        mapper.registerModule(mod);

        LOGGER.info("initialized MongoDB Client DB name LOGGER {}" + database.getName());
    }

    ////////////////////////////
    // Month operations.

    public Month insertMonth(Month theMonth) {

        Document doc = new Document("month", theMonth.getMonth());
        collection.insertOne(doc);
        return theMonth;
    }

    public boolean deleteMonth(String id) {
        try {
            DeleteResult result = collection.deleteOne(new Document("_id", new ObjectId(id)));
            if(result.getDeletedCount() > 0){
                return true;
            }
            
        } catch (MongoException e) {
            System.out.println("Error ==> " + e);
        }
        return false;
    }
    ////////////////////////////
    // Income Operations

    public Income insertIncome(String theMonth, Income theIncome) {

        Document doc = new Document("title", theIncome.getTitle())
                .append("amount", theIncome.getAmount())
                .append("id", new ObjectId());

        collection.updateOne(
                new Document("month", new String(theMonth)),
                new Document("$push", new Document("incomes", doc)));

        return theIncome;
    }

    ////////////////////////////
    // Expenses Operations.
    public void insertExpense(String theMonth, Expense theExpense) {

        Document doc = new Document("title", theExpense.getTitle())
                .append("amount", theExpense.getAmount())
                .append("category", theExpense.getCategory())
                .append("status", theExpense.getStatus())
                .append("bank", theExpense.getBank());

        collection.updateOne(
                new Document("month", new String(theMonth)),
                new Document("$push", new Document("expenses", doc)));
    }

    public Month findAll(String theMonth) {
        FindIterable<Document> records = collection.find(new Document("month", theMonth));

        Month monthFinancial = null;
        try {
            for (Document doc : records) {
                // Deserialize each Document to a MonthFinancial object
                monthFinancial = mapper.readValue(doc.toJson(), Month.class);
            }
        } catch (JsonProcessingException e) {
            System.out.println("Error ==> " + e.getMessage());

        }
        // If no documents were found, return an empty object instead of null
        return monthFinancial != null ? monthFinancial : new Month();
    }

    public static void main(String[] args) {
        MonthService service = new MonthService();

        Month theMonth = new Month();
        Expense theExpense = new Expense("STC Biles", 2565465, "Basic Expenses", "STCPay", "unPaid");

        String month = "September";
        Income theIncome = new Income("Salary", 10000);
        // theMonth.setMonth(month);
        // service.insertMonth(theMonth);

        // service.insertIncome(month, theIncome);
        // service.insertExpense(month, theExpense);

        System.out.println(service.findAll(month));
    }
}
