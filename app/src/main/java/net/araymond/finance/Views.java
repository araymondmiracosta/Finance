package net.araymond.finance;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public class Views extends AppCompatActivity {
    public static void generateTransactionScrollView(Context context, LinearLayout scrollLinearLayout, Resources resources) {
        scrollLinearLayout.removeAllViews();

        int padding = (int) ((resources.getDisplayMetrics().density * 20 + 0.5f));

        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        int horizontalMargins = Math.round(TypedValue.applyDimension    // Margins
                (TypedValue.COMPLEX_UNIT_SP, 25, resources.getDisplayMetrics()));

        int backgroundColor = ResourcesCompat.getColor
                (resources, R.color.buttonBackgroundTint, null);
        int textColor = ResourcesCompat.getColor
                (resources, R.color.buttonTextColor, null);

        int positiveColor = ResourcesCompat.getColor
                (resources, R.color.positiveColor, null);
        int negativeColor = ResourcesCompat.getColor
                (resources, R.color.negativeColor, null);

        Space beginningSpace = new Space(context);
        beginningSpace.setMinimumWidth(padding);
        scrollLinearLayout.addView(beginningSpace);

        for (Account account : Values.accounts) {
            for (int i = account.getTransactions().size() - 1; i >= 0; i--) {
                Transaction transaction = account.getTransactions().get(i);
                TextView transactionAmount = new TextView(context);       // Text box for transaction amount
                TextView accountName = new TextView(context);          // Text box for account name
                TextView transactionCategory = new TextView(context);     // Text box for transaction category
                TextView transactionDate = new TextView(context);        // Text box transaction date
                TextView transactionTime = new TextView(context);       // Text box for transaction time
                GridLayout gridLayout = new GridLayout(context);    // Layout to hold text views
                Space nextSpace = new Space(context);
                Space middleSpace = new Space(context);
                Space bottomSpace = new Space(context);
                Space topSpace = new Space(context);

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Values.dateFormat);
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(Values.timeFormat);

                String transactionAmountText;
                String accountNameText;
                String transactionCategoryText;
                String transactionDateText;
                String transactionTimeText;

                nextSpace.setMinimumWidth(padding);
                middleSpace.setMinimumHeight(padding);
                bottomSpace.setMinimumHeight((int) Math.round(padding * 0.80));
                topSpace.setMinimumHeight((int) Math.round(padding * 0.80));

                gridLayout.setOrientation(GridLayout.HORIZONTAL);
                gridLayout.setColumnCount(2);
                gridLayout.setBackgroundColor(backgroundColor);
                gridLayout.setBackground(resources.getDrawable(R.drawable.button, null));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(horizontalMargins, horizontalMargins / 10, horizontalMargins, horizontalMargins / 10);

                // Transaction amount
                if (transaction.getAmount() < 0) {
                    transactionAmountText = "(" + (decimalFormat.format(transaction.getAmount() * -1)) + ")";
                    transactionAmount.setTextColor(negativeColor);
                }
                else {
                    transactionAmountText = " " + decimalFormat.format(transaction.getAmount()) + " ";
                    transactionAmount.setTextColor(positiveColor);
                }
                transactionAmount.setLayoutParams(params);
                transactionAmount.setTypeface(Typeface.DEFAULT_BOLD);
                transactionAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                transactionAmount.setText(transactionAmountText);

                // End transaction amount

                // Account name
                SpannableString accountSpan = new SpannableString(
                        "Account: " + account.getName()
                );
                StyleSpan accountSpanStyle = new StyleSpan(Typeface.BOLD);
                accountSpan.setSpan(accountSpanStyle, 9, accountSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                accountName.setLayoutParams(params);
                accountName.setTextColor(textColor);
                accountName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                accountName.setText(accountSpan);

                // End account name

                // Transaction category
                SpannableString categorySpan = new SpannableString(
                        "Category: " + transaction.getCategory()
                );
                StyleSpan categorySpanStyle = new StyleSpan(Typeface.BOLD);
                categorySpan.setSpan(categorySpanStyle, 9, categorySpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                transactionCategory.setLayoutParams(params);
                transactionCategory.setTextColor(textColor);
                transactionCategory.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                transactionCategory.setText(categorySpan);
                transactionCategory.setWidth(horizontalMargins * 8);

                // End transaction category

                // Transaction date
                SpannableString dateSpan = new SpannableString(
                        transaction.getDate().format(dateTimeFormatter) + " @ " +
                        transaction.getTime().format(timeFormatter));
                StyleSpan dateSpanStyle = new StyleSpan(Typeface.BOLD);
                dateSpan.setSpan(dateSpanStyle, 0, dateSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                transactionDate.setLayoutParams(params);
                transactionDate.setTextColor(textColor);
                transactionDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                transactionDate.setText(dateSpan);

                // End transaction date

                // Transaction time
                transactionTimeText = transaction.getTime().format(timeFormatter);
                transactionTime.setLayoutParams(params);
                transactionTime.setTextColor(textColor);
                transactionTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                transactionTime.setText(transactionTimeText);

                // End transaction time

                gridLayout.addView(transactionCategory);
                gridLayout.addView(topSpace);
                gridLayout.addView(accountName);
                gridLayout.addView(transactionAmount);
//                gridLayout.addView(transactionAmount, new GridLayout.LayoutParams(
//                        GridLayout.spec(1, GridLayout.RIGHT),
//                        GridLayout.spec(1, GridLayout.RIGHT)
//                ));
                gridLayout.addView(transactionDate);
                gridLayout.addView(bottomSpace);
//                gridLayout.addView(transactionTime);

                gridLayout.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Toast.makeText(context, transactionAmountText, Toast.LENGTH_SHORT).show();
                    }
                });

                scrollLinearLayout.addView(gridLayout);
                scrollLinearLayout.addView(middleSpace);
            }
        }
    }

    public static void generateAccountScrollView(Context context, LinearLayout scrollLinearLayout, Resources resources) {
        scrollLinearLayout.removeAllViews();

        int padding = (int) ((resources.getDisplayMetrics().density * 20 + 0.5f));

        DecimalFormat decimalFormat = new DecimalFormat("#.00");

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
            accountBalance.setText(decimalFormat.format(account.getBalance()));

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
