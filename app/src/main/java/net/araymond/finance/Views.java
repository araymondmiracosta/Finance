package net.araymond.finance;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.text.NumberFormat;
import java.util.Locale;

public class Views extends AppCompatActivity {
    public static void generateAccountScrollView(Context context, LinearLayout scrollLinearLayout, int verticalMargins, Resources resources) {
        scrollLinearLayout.removeAllViews();

        int padding = (int) ((resources.getDisplayMetrics().density * 20 + 0.5f));

        Locale locale = new Locale(Values.language, Values.country);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

        int horizontalMargins = Math.round(TypedValue.applyDimension    // Margins
                (TypedValue.COMPLEX_UNIT_SP, 25, resources.getDisplayMetrics()));

        int backgroundColor = ResourcesCompat.getColor
                (resources, R.color.buttonBackgroundTint, null);
        int textColor = ResourcesCompat.getColor
                (resources, R.color.buttonTextColor, null);

        Space beginningSpace = new Space(context);
        beginningSpace.setMinimumWidth(padding);
        scrollLinearLayout.addView(beginningSpace);

        for (Account account : Values.accounts) {
            TextView accountName = new TextView(context);          // Text box for account name
            TextView accountBalance = new TextView(context);       // Text box for account balance
            LinearLayout linearLayout = new LinearLayout(context);    // Frame layout to hold both text views
            Space nextSpace = new Space(context);
            Space middleSpace = new Space(context);
            Space bottomSpace = new Space(context);
            Space topSpace = new Space(context);

            nextSpace.setMinimumWidth(padding);
            middleSpace.setMinimumHeight(padding);
            bottomSpace.setMinimumHeight((int) Math.round(padding * 0.80));
            topSpace.setMinimumHeight((int) Math.round(padding * 0.80));

            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setBackgroundColor(backgroundColor);
            linearLayout.setBackground(resources.getDrawable(R.drawable.button, null));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(horizontalMargins, 0, horizontalMargins, 0);

            accountName.setLayoutParams(params);
            accountName.setTextColor(textColor);
            accountName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            accountName.setText(account.getName());

            accountBalance.setLayoutParams(params);
            accountBalance.setTextColor(textColor);
            accountBalance.setTypeface(Typeface.DEFAULT_BOLD);
            accountBalance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            accountBalance.setText(numberFormat.format(account.getBalance()));

            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.addView(topSpace);
            linearLayout.addView(accountName);
            linearLayout.addView(middleSpace);
            linearLayout.addView(accountBalance);
            linearLayout.addView(bottomSpace);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(context, account.getName() + "\n" + account.getBalance(), Toast.LENGTH_SHORT).show();
                }
            });

            scrollLinearLayout.addView(linearLayout);
            scrollLinearLayout.addView(nextSpace);
        }
    }
}
