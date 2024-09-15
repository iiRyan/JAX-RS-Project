package com.expensetracker.rest.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.expensetracker.rest.db.MongoDBClient;
import com.expensetracker.rest.model.Expense;
import com.expensetracker.rest.model.Income;
import com.expensetracker.rest.model.MonthFinancial;
import com.expensetracker.rest.model.ObjectIdDeserializer;
import com.expensetracker.rest.model.ObjectIdSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MonthFinancialService {
    private static final Logger LOGGER = LogManager.getLogger(MonthFinancialService.class);

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MonthFinancialService() {

        mongoClient = MongoDBClient.INSTANCE.getMongoClient();
        database = mongoClient.getDatabase("myExpenses");
        collection = database.getCollection("monthlyBudget");
        LOGGER.info("initialized MongoDB Client DB name LOGGER {}" + database.getName());
    }

    // Star insertion operation.
    public void insertMonth(MonthFinancial monthFinancial) {

        Document doc = new Document("month", monthFinancial.getMonth());
        collection.insertOne(doc);
    }

    public void insertIncome(String theMonth, Income theIncome) {

        Document doc = new Document("title", theIncome.getTitle())
                .append("amount", theIncome.getAmount());

        collection.updateOne(
                new Document("month", new String(theMonth)),
                new Document("$push", new Document("incomes", doc)));
    }

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

    // End insert operation

    public MonthFinancial findAll(String theMonth) {

        FindIterable<Document> records = collection.find(new Document("month", theMonth));
        MonthFinancial listOfFinancial = new MonthFinancial();

        ObjectMapper mapper = new ObjectMapper();
        // Register the custom deserializer for ObjectId
        SimpleModule mod = new SimpleModule("ObjectIdModule", new Version(1, 0, 0, null, null, null));
        mod.addDeserializer(ObjectId.class, new ObjectIdDeserializer()); // Add custom deserializer
        mapper.registerModule(mod);

        MonthFinancial object = null;
        for (Document doc : records) {
            try {
                
                System.out.println(doc.toJson());
                // Here we Serialize POJO into JSON.
                object = mapper.readValue(doc.toJson(), MonthFinancial.class);

            } catch (JsonProcessingException e) {

                e.printStackTrace();
            }

        }

        return object;
    }

    public static void main(String[] args) {
        MonthFinancialService service = new MonthFinancialService();

        MonthFinancial theMonth = new MonthFinancial();
        Expense theExpense = new Expense("STC Biles", 2565465, "Basic Expenses", "STCPay", "unPaid");

        String month = "today";
        Income theIncome = new Income("Salary", 10000);
        // theMonth.setMonth(month);
        // service.insertMonth(theMonth);

        // service.insertIncome(month, theIncome);
        // service.insertExpense(month, theExpense);

        System.out.println(service.findAll(month));
    }
}
