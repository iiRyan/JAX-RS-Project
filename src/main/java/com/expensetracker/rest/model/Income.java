package com.expensetracker.rest.model;


public class Income {

    private String title;
    private int amount;

    public Income() {
    }

    public Income(String title, int amount) {
        this.title = title;
        this.amount = amount;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Income [title=" + title + ", amount=" + amount + "]";
    }
    
}
