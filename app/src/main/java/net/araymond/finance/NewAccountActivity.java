package net.araymond.finance;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

public class NewAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newaccount);

        Toolbar newAccountToolbar = (Toolbar) findViewById(R.id.newAccountToolbar);
        setSupportActionBar(newAccountToolbar);
        getSupportActionBar().setTitle("New Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button applyButton = (Button) findViewById(R.id.applyButton);
        TextInputEditText accountNameBox = (TextInputEditText) findViewById(R.id.accountName);
        TextInputEditText accountBalanceBox = (TextInputEditText) findViewById(R.id.accountBalance);

        applyButton.setEnabled(false);

        final TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Abstract stub
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    Double.valueOf(accountBalanceBox.getText().toString());
                    if (accountNameBox.getText().toString().length() == 0 ||
                            accountBalanceBox.getText().toString().length() == 0) {
                        applyButton.setEnabled(false);
                    }
                    else {
                        applyButton.setEnabled(true);
                    }
                } catch (Exception exception) {
                    applyButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Abstract stub
            }
        };

        accountNameBox.addTextChangedListener(watcher);
        accountBalanceBox.addTextChangedListener(watcher);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {
        TextInputEditText accountNameBox = (TextInputEditText) findViewById(R.id.accountName);
        TextInputEditText accountBalanceBox = (TextInputEditText) findViewById(R.id.accountBalance);
        String accountName = accountNameBox.getText().toString();
        boolean nameCheck = true;
        for (Account account : Values.accounts) {
            if (accountName.compareTo(account.getName()) == 0) {
                nameCheck = false;
                Toast.makeText(this, "New account name cannot be the same as an existing account.", Toast.LENGTH_LONG).show();
            }
        }
        if (nameCheck) {
            Values.accounts.add(new Account(accountNameBox.getText().toString(), Double.parseDouble(accountBalanceBox.getText().toString())));
            if (Utility.writeSaveData(this)) {
                Toast.makeText(this, "Data saved! Account size:" + Values.accounts.size(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Data was not saved properly.", Toast.LENGTH_LONG).show();
            }
            onBackPressed();
        }
    }
}