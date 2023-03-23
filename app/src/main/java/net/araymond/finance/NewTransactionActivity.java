package net.araymond.finance;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
                        transactionDate.setText(localTime.format(timeFormatter));
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

        transactionCategory.setAdapter(new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, Values.categories));


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

//        Values.accounts.add(new Account(transactionDescription.getText().toString(), Double.parseDouble(transactionAmount.getText().toString())));
 //       if (Utility.writeSaveData(this)) {
 //           Toast.makeText(this, "Data saved! Account size:" + Values.accounts.size(), Toast.LENGTH_LONG).show();
 //       }
 //       else {
 //           Toast.makeText(this, "Data was not saved properly.", Toast.LENGTH_LONG).show();
 //       }
        onBackPressed();
    }
}