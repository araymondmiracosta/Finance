package net.araymond.finance;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Utility {

    public static int indexFromName(String accountName) {
        for (int i = 0; i < Values.accounts.size(); i++) {
            if (accountName.compareTo(Values.accounts.get(i).getName()) == 0) {
                return i;
            }
        }
        return -1;
    }

    public static void readAccounts() {
        int accountsSize = Values.accounts.size();
        String[] accountsNames = new String[accountsSize];

        for (int i = 0; i < accountsSize; i++) {
            accountsNames[i] = Values.accounts.get(i).getName();
        }

        Values.accountsNames = accountsNames;
    }

    public static void readCategories() {
        ArrayList<Transaction> transactions;

        for (Account account : Values.accounts) {
            transactions = account.getTransactions();
            for (Transaction transaction : transactions) {
                Values.categories.add(transaction.getCategory());
            }
        }
    }

    public static boolean readSaveData(Context context) {
        try {
            FileInputStream inputStream = context.openFileInput("ledger");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Values.accounts = (ArrayList<Account>) objectInputStream.readObject();

            objectInputStream.close();
            inputStream.close();

            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public static boolean writeSaveData(Context context) {
        try {
            FileOutputStream outputStream = context.openFileOutput("ledger", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(Values.accounts);

            outputStream.flush();
            outputStream.close();
            objectOutputStream.close();

            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }
}
