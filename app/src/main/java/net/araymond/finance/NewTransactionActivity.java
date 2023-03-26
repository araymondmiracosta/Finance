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
import androidx.core.content.res.ResourcesCompat;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class NewTransactionActivity extends AppCompatActivity {
    boolean isPositive = false;
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

        // Time selection
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(Values.timeFormat);
        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setTitleText("Select transaction time").setTimeFormat(Values.timeScheme).build();

        localTime = LocalTime.now();
        transactionTime.setText(localTime.format(timeFormatter));

        transactionTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialTimePicker.show(getSupportFragmentManager(), "Material_Time_Picker");
            }
        });

        materialTimePicker.addOnPositiveButtonClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        localTime = LocalTime.of(materialTimePicker.getHour(), materialTimePicker.getMinute());
                        transactionTime.setText(localTime.format(timeFormatter));
                    }
                }
        );

        // End time selection

        // Date selection
        Calendar calendar = Calendar.getInstance();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Values.dateFormat);

        localDate = LocalDate.now();
        transactionDate.setText(localDate.format(dateTimeFormatter));

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Select transaction date");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.setTimeInMillis(selection);
                        localDate = LocalDate.of(selectedDate.get(Calendar.YEAR),
                                (selectedDate.get(Calendar.MONTH) + 1), (selectedDate.get(Calendar.DAY_OF_MONTH) + 1));
                        transactionDate.setText(localDate.format(dateTimeFormatter));
                    }
                }
        );

        transactionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                datePickerDialog.show();
                materialDatePicker.show(getSupportFragmentManager(), "Material_Date_Picker");
            }
        });
        // End date selection

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

        // Begin transaction category
        transactionCategory.setAdapter(new ArrayAdapter<>
                (this, android.R.layout.simple_dropdown_item_1line, Values.categories));

        // End transaction category

        // Begin transaction account
        String[] accountsNames = Values.accountsNames.toArray(new String[0]);
        transactionAccount.setAdapter(new ArrayAdapter<>
                (this, android.R.layout.simple_dropdown_item_1line, accountsNames));

        // End transaction account

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
            // Ensure that the apply button is only available if EVERY field is valid
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    // Check transaction amount is valid
                    Double.parseDouble(transactionAmount.getText().toString());
                    applyButton.setEnabled(
                            // Check transaction account is valid
                            Utility.indexFromName(transactionAccount.getText().toString()) != -1 &&
                            transactionCategory.getText().toString().length() != 0
//                             && transactionDescription.getText().toString().length() != 0
                    );
                } catch (Exception exception) {
                    applyButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Abstract stub
            }
        };

        transactionAmount.addTextChangedListener(watcher);
        transactionAccount.addTextChangedListener(watcher);
        transactionCategory.addTextChangedListener(watcher);
//        transactionDescription.addTextChangedListener(watcher);
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

        double amount = Double.parseDouble(transactionAmount.getText().toString());
        int accountIndex = Utility.indexFromName(transactionAccount.getText().toString());
        String category = transactionCategory.getText().toString();
        String description = transactionDescription.getText().toString();

        if (!(isPositive)) {
            amount *= -1;
        }

        Values.accounts.get(accountIndex).newTransaction(
                category, description, amount, localDate, localTime);

        if (Utility.writeSaveData(this)) {
            Toast.makeText(this, "New transaction added! Transaction size on account \""
            + Values.accounts.get(accountIndex).getName() + "\": " + Values.accounts.get(accountIndex)
            , Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Data was not saved properly.", Toast.LENGTH_SHORT).show();
        }

        onBackPressed();
    }
}