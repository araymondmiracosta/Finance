package net.araymond.finance;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.text.NumberFormat;
import java.util.Locale;

public class Utility extends AppCompatActivity {
    public static void generateAccountScrollView(Context context, LinearLayout scrollLinearLayout, int verticalMargins, Resources resources) {
        scrollLinearLayout.removeAllViews();

        int padding = (int) ((resources.getDisplayMetrics().density * 20 + 0.5f));

        Locale locale = new Locale(Values.language, Values.country);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

        int horizontalMargins = Math.round(TypedValue.applyDimension    // Margins
                (TypedValue.COMPLEX_UNIT_SP, 25, resources.getDisplayMetrics()));

        int backgroundColor = ResourcesCompat.getColor
                (resources, com.google.android.material.R.color.material_dynamic_neutral_variant20, null);
        int textColor = ResourcesCompat.getColor
                (resources, com.google.android.material.R.color.material_dynamic_primary80, null);

        Space beginningSpace = new Space(context);
        beginningSpace.setMinimumWidth(padding);
        scrollLinearLayout.addView(beginningSpace);

        for (Account account : Values.accounts) {
            TextView accountName = new TextView(context);          // Text box for account name
            TextView accountBalance = new TextView(context);       // Text box for account balance
            LinearLayout linearLayout = new LinearLayout(context);    // Frame layout to hold both text views
            Space nextSpace = new Space(context);
            Space middleSpace = new Space(context);

            nextSpace.setMinimumWidth(padding);

            middleSpace.setMinimumHeight(padding);

            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setBackgroundColor(backgroundColor);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(horizontalMargins / 2, verticalMargins, horizontalMargins / 2, 0);

            accountName.setLayoutParams(params);
            accountName.setTextColor(textColor);
            accountName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            accountName.setText(account.getName());

            accountBalance.setLayoutParams(params);
            accountBalance.setTextColor(textColor);
            accountBalance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            accountBalance.setText(numberFormat.format(account.getBalance()));

            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.addView(accountName);
            linearLayout.addView(middleSpace);
            linearLayout.addView(accountBalance);

            scrollLinearLayout.addView(linearLayout);
            scrollLinearLayout.addView(nextSpace);
        }
    }
}
