package net.araymond.finance;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Transaction implements Serializable {
    String category, description;
    double amount;
    LocalDateTime dateAndTime;

    public Transaction(String category, String description, double amount, LocalDateTime dateAndTime) {
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.dateAndTime = dateAndTime;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }
}
