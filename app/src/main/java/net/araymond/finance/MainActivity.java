package net.araymond.finance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean executeOnResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executeOnResume = false;

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Finance");

        initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (executeOnResume) {
            verifyAccountsSize();
        }
        else {
            executeOnResume = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.settings:
                Intent newIntent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(newIntent);
                return true;

            case R.id.about:
                return true;

            default:
                return true;
        }
    }

    public void sendMessage(View view) {
        Intent newIntent = new Intent(MainActivity.this, NewTransactionActivity.class);
        MainActivity.this.startActivity(newIntent);
    }

    public void initialize() {
        if (Utility.readSaveData(this)) {
            Toast.makeText(this, "Data read successfully! Account size: " + Values.accounts.size(), Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Data was not read successfully.", Toast.LENGTH_LONG).show();
        }
        verifyAccountsSize();
    }

    public void verifyAccountsSize() {
        int accountsSize = Values.accounts.size();
        Button transactionButton = (Button) findViewById(R.id.transactionButton);
        if (accountsSize == 0) {
            transactionButton.setEnabled(false);
        }
        else {
            Utility.readCategories();
            Utility.readAccounts();
            Views.generateAccountScrollView(this, (LinearLayout) findViewById(R.id.scrollLinearLayout)
                    , getResources());
            Views.generateTransactionScrollView(this, (LinearLayout) findViewById(R.id.transactionScrollView)
                    , getResources());
            transactionButton.setEnabled(true);
        }
    }
}