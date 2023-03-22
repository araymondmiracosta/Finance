package net.araymond.finance;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;

public class NewTransactionActivity extends AppCompatActivity {
    boolean isPositive = false;
    Context context = this;
    LocalDate localDate;
    LocalTime localTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtransaction);

        Toolbar newTransactionToolbar = (Toolbar) findViewById(R.id.newTransactionToolbar);
        setSupportActionBar(newTransactionToolbar);
        getSupportActionBar().setTitle("New Transaction");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextInputEditText transactionAmount = (TextInputEditText) findViewById(R.id.transactionAmount);
        AutoCompleteTextView transactionAccount = (AutoCompleteTextView) findViewById(R.id.transactionAccount);
        AutoCompleteTextView transactionCategory = (AutoCompleteTextView) findViewById(R.id.transactionCategory);
        TextInputEditText transactionDescription = (TextInputEditText) findViewById(R.id.transactionDescription);
        TextInputEditText transactionDate = (TextInputEditText) findViewById(R.id.transactionDate);
        TextInputEditText transactionTime = (TextInputEditText) findViewById(R.id.transactionTime);
        Button addButton = (Button) findViewById(R.id.addButton);
        Button negativeButton = (Button) findViewById(R.id.negativeButton);
        Button applyButton = (Button) findViewById(R.id.applyButton);

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    localDate = LocalDate.of(year, month, day);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        applyButton.setEnabled(false);
        transactionCategory.setThreshold(0);

        addButton.setBackgroundColor(ResourcesCompat.getColor
                (getResources(), R.color.buttonBackgroundTintDisabled, null));
        addButton.setTextColor(ResourcesCompat.getColor
                (getResources(), R.color.buttonTextColorDisabled, null));
        negativeButton.setBackgroundColor(ResourcesCompat.getColor
                (getResources(), R.color.buttonBackgroundTintPressed, null));
        negativeButton.setTextColor(ResourcesCompat.getColor
                (getResources(), R.color.buttonTextColor, null));

        transactionCategory.setAdapter(new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, Values.categories));

        transactionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                isPositive = true;
                addButton.setBackgroundColor(ResourcesCompat.getColor
                        (getResources(), R.color.buttonBackgroundTintPressed, null));
                addButton.setTextColor(ResourcesCompat.getColor
                        (getResources(), R.color.buttonTextColor, null));

                negativeButton.setBackgroundColor(ResourcesCompat.getColor
                        (getResources(), R.color.buttonBackgroundTintDisabled, null));
                negativeButton.setTextColor(ResourcesCompat.getColor
                        (getResources(), R.color.buttonTextColorDisabled, null));
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                isPositive = false;
                addButton.setBackgroundColor(ResourcesCompat.getColor
                        (getResources(), R.color.buttonBackgroundTintDisabled, null));
                addButton.setTextColor(ResourcesCompat.getColor
                        (getResources(), R.color.buttonTextColorDisabled, null));

                negativeButton.setBackgroundColor(ResourcesCompat.getColor
                        (getResources(), R.color.buttonBackgroundTintPressed, null));
                negativeButton.setTextColor(ResourcesCompat.getColor
                        (getResources(), R.color.buttonTextColor, null));
            }
        });

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
        transactionCategory.addTextChangedListener(watcher);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {
        TextInputEditText transactionAmount = (TextInputEditText) findViewById(R.id.transactionAmount);
        AutoCompleteTextView transactionAccount = (AutoCompleteTextView) findViewById(R.id.transactionAccount);
        AutoCompleteTextView transactionCategory = (AutoCompleteTextView) findViewById(R.id.transactionCategory);
        TextInputEditText transactionDescription = (TextInputEditText) findViewById(R.id.transactionDescription);
        TextInputEditText transactionDate = (TextInputEditText) findViewById(R.id.transactionDate);
        TextInputEditText transactionTime = (TextInputEditText) findViewById(R.id.transactionTime);
        Button addButton = (Button) findViewById(R.id.addButton);
        Button negativeButton = (Button) findViewById(R.id.negativeButton);
        Button applyButton = (Button) findViewById(R.id.applyButton);

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