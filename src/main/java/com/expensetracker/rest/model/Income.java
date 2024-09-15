package com.expensetracker.rest.model;

import org.bson.types.ObjectId;

public class Income {

    
    private String _id;
    private String title;
    private int amount;

    public Income() {
    }

    public Income(String title, int amount) {
        this.title = title;
        this.amount = amount;
    }

    public String getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        if (_id != null) {
            this._id = _id.toHexString();
        }
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
        return "Income [id=" + _id + ", title=" + title + ", amount=" + amount + "]";
    }
   
}
