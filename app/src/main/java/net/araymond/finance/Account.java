package net.araymond.finance;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Account {
    String name;
    double balance;
    ArrayList<Transaction> transactions;

    public Account(String name, double balance) {
        this.name = name;
        this.balance = balance;
        transactions = new ArrayList<Transaction>();
    }

    public void newTransaction(String category, String description, double amount, LocalDateTime dateAndTime) {
        transactions.add(new Transaction(category, description, amount, dateAndTime));
    }

    public void removeTransaction(int index) {
        transactions.remove(index);
    }
}
