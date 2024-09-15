package com.expensetracker.rest.model;

import java.util.List;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class MonthFinancial {

    @JsonProperty("_id")
    
    private String id;
    private String month;
    private List<Income> incomes;
    private List<Expense> expenses;

    public MonthFinancial() {
    }

    public MonthFinancial(String month, List<Income> incomes, List<Expense> expenses) {
        this.month = month;
        this.incomes = incomes;
        this.expenses = expenses;

    }

    public String getId() {
        return id;
    }

    public void setId(ObjectId id) {
        if (id != null) {
            this.id = id.toHexString();
        }

    }

    public List<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<Income> incomes) {
        this.incomes = incomes;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "MonthFinancial [id=" + id + ", month=" + month + ", incomes=" + incomes + ", expenses=" + expenses
                + "]";
    }

}
