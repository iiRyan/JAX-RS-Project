package com.expensetracker.rest.model;

import org.bson.types.ObjectId;

public class Expense {

    private String _id;
    private String title;
    private int amount;
    private String category;
    private String bank;
    private String status;

    public Expense() {
    }

    public Expense(String title, int amount, String category, String bank,
            String status) {

        this.title = title;
        this.amount = amount;
        this.category = category;
        this.bank = bank;
        this.status = status;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Expense [_id=" + _id + ", title=" + title + ", amount=" + amount + ", category=" + category + ", bank="
                + bank + ", status=" + status + "]";
    }

   

}
