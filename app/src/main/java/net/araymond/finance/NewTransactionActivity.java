package net.araymond.finance;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

public class NewTransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtransaction);

        Toolbar newTransactionToolbar = (Toolbar) findViewById(R.id.newTransactionToolbar);
        setSupportActionBar(newTransactionToolbar);
        getSupportActionBar().setTitle("New Transaction");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button applyButton = (Button) findViewById(R.id.applyButton);
        TextInputEditText transactionDescription = (TextInputEditText) findViewById(R.id.transactionDescription);
        TextInputEditText transactionAmount = (TextInputEditText) findViewById(R.id.transactionAmount);
        AutoCompleteTextView transactionCategory = (AutoCompleteTextView) findViewById(R.id.transactionCategory);

        applyButton.setEnabled(false);
        transactionCategory.setThreshold(0);

        transactionCategory.setAdapter(new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, Values.categories));

        final TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Abstract stub
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    Double.valueOf(transactionAmount.getText().toString());
                    if (transactionDescription.getText().toString().length() == 0 ||
                            transactionAmount.getText().toString().length() == 0 ||
                            transactionCategory.getText().toString().length() == 0) {
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

        transactionDescription.addTextChangedListener(watcher);
        transactionAmount.addTextChangedListener(watcher);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {
        TextInputEditText transactionDescription = (TextInputEditText) findViewById(R.id.transactionDescription);
        TextInputEditText transactionAmount = (TextInputEditText) findViewById(R.id.transactionAmount);
        Values.accounts.add(new Account(transactionDescription.getText().toString(), Double.parseDouble(transactionAmount.getText().toString())));
        if (Utility.writeSaveData(this)) {
            Toast.makeText(this, "Data saved! Account size:" + Values.accounts.size(), Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Data was not saved properly.", Toast.LENGTH_LONG).show();
        }
        onBackPressed();
    }
}