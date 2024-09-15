package com.expensetracker.rest.service;

import java.util.*;

import org.bson.Document;
import org.bson.types.ObjectId;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.expensetracker.rest.db.MongoDBClient;
import com.expensetracker.rest.exception.MissingFieldException;
import com.expensetracker.rest.model.Expense;
import com.expensetracker.rest.model.Income;
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

public class IncomeService {
    private static final Logger LOGGER = LogManager.getLogger(IncomeService.class);
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public IncomeService() {
        mongoClient = MongoDBClient.INSTANCE.getMongoClient();
        database = mongoClient.getDatabase("myExpenses");
        collection = database.getCollection("incomes");
        LOGGER.info("initialized MongoDB Client DB name {}" + database.getName());
    }

    public Income insertIncome(Income income) {
        LOGGER.info("Inserting Income...");
        if (income.getTitle() == null || income.getTitle().isEmpty()) {
            throw new MissingFieldException("Title is missing or null");
        }
       

        Document doc = new Document("title", income.getTitle())
                .append("amount", income.getAmount());                
        collection.insertOne(doc);
        LOGGER.info("Income inserted {}." + income);
        return income;
    }

    public List<Expense> insertExpense(String theMonth,Expense expense){
        LOGGER.info("Adding expense to income with ID: " + theMonth);

        List<Expense> expensesList = new ArrayList<>();
        Document doc = new Document("title",expense.getTitle())
        .append("amount", expense.getAmount())
        .append("category", expense.getCategory())
        .append("status", expense.getStatus())
        .append("bank", expense.getBank());
        expensesList.add(expense);

        collection.updateOne(
        new Document("month", new String(theMonth)),  
        new Document("$push", new Document("expenses", doc))
    );

        return expensesList;
    }


    public List<Income> getIncomes() {

        FindIterable<Document> incomes = collection.find();
        List<Income> incomeList = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule mod = new SimpleModule("ObjectId", new Version(1, 0, 0, null, null, null));
        mod.addDeserializer(ObjectId.class, new ObjectIdDeserializer());
        mapper.registerModule(mod);

        for (Document doc : incomes) {
            try {
                Income income = mapper.readValue(doc.toJson(), Income.class);
                System.out.println(doc.toJson());
                incomeList.add(income);
            } catch (JsonProcessingException e) {

                e.printStackTrace();
            }
        }
        return incomeList;
    }

    public void deleteIncome(String id) {
        try {
            DeleteResult result = collection.deleteOne(new Document("_id", new ObjectId(id)));
            System.out.println("Deleted document count: " + result.getDeletedCount());
        } catch (MongoException e) {
            System.err.println("Unable to delete due to an error: " + e);

        }
    }

    public static void main(String[] args) {
        IncomeService service = new IncomeService();
        Expense theExpense = new Expense("STC Biles", 700, "Basic Expenses", "InmaPay", "unPaid");
        List<Expense> expenses = new ArrayList<>();
        expenses.add(theExpense);
        Income income = new Income("Salary", 2800);
        System.out.println(service.insertIncome(income));
        
        // service.insertExpense("January", theExpense);
        // //  System.out.println(service.insertIncome(income));
        // List<Income> incomesList =  service.getIncomes();

        // for (Income income2 : incomesList) {
        //     System.out.println(income2);
        // }
        // MongoClient  mongoClient = MongoDBClient.INSTANCE.getMongoClient();

        // MongoDatabase database = mongoClient.getDatabase("myExpenses");
        // MongoCollection<Document> collection = database.getCollection("incomes");
        // FindIterable<Document> incomes = collection.find();
        // ObjectId objectId = null;
        // for (Document document : incomes) {
            
        //     System.out.println(document.get("_id"));
        // }
       

    }
}
