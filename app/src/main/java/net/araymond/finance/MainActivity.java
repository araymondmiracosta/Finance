package net.araymond.finance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Finance");

        TextView totalTextView = (TextView) findViewById(R.id.total);
        Values.total = 1000;
        totalTextView.setText(Integer.toString(Values.total));
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
        TextView totalTextView = (TextView) findViewById(R.id.total);
        Values.total = Values.total + 1000;
        totalTextView.setText(Integer.toString(Values.total));
    }
}