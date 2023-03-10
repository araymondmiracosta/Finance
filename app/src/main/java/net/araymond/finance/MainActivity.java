package net.araymond.finance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean executeOnResume;
    int verticalMargins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executeOnResume = false;

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Finance");

        if (Utility.readSaveData(this)) {
            Toast.makeText(this, "Data read successfully! Account size: " + Values.accounts.size(), Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Data was not read successfully.", Toast.LENGTH_LONG).show();
        }

        verticalMargins = getSupportActionBar().getHeight() * 3;

        Views.generateAccountScrollView(this, (LinearLayout) findViewById(R.id.scrollLinearLayout)
                , verticalMargins, getResources());

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (executeOnResume) {
            Views.generateAccountScrollView(this, (LinearLayout) findViewById(R.id.scrollLinearLayout)
                    ,verticalMargins, getResources());
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

            default:
                return true;
        }
    }

    public void sendMessage(View view) {
    }
}