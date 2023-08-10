package com.example.madassignment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Equally Breakdown
        Button b1 = findViewById(R.id.button);
        //Custom Breakdown by Percentage/Ratio
        Button b2 = findViewById(R.id.button2);
        //Combination Breakdown
        Button b3 = findViewById(R.id.button3);

        //When
        b1.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, equallyBreakdown.class);
                    startActivity(intent);
                }
        );

        b2.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, customBreakdownPercentage.class);
                    startActivity(intent);
                }
        );

        b3.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, customBreakdownByAmount.class);
                    startActivity(intent);
                }
        );


    }

    public static void showInputError(Context c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Error")
                .setMessage("All input cannot be empty or invalid. Please enter a valid number.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Generate multiple EditText in certain layout
    public static void generateEditText(int num, ConstraintLayout layout, Context c) {
        LinearLayout linearLayout = new LinearLayout(c);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < num; i++) {
            // Create a new EditText
            EditText editText = new EditText(c);

            // Set attributes for the EditText (e.g., width, height, hint)
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            editText.setLayoutParams(params);

            int marginBottomInDp = 10;
            float scale = c.getResources().getDisplayMetrics().density;
            int marginBottomInPx = (int) (marginBottomInDp * scale + 0.5f);
            params.setMargins(0, 0, 0, marginBottomInPx);

            editText.setHint("eg. 20% ");
            editText.setHintTextColor(Color.WHITE);
            editText.setTextColor(Color.WHITE);
            editText.setTextSize(17);
            editText.setTypeface(ResourcesCompat.getFont(c, R.font.cabin_sketch_bold));

            // Add the EditText to the LinearLayout
            linearLayout.addView(editText);
        }

        // Add the LinearLayout to the ConstraintLayout
        layout.addView(linearLayout);
    }


  public static String displayResult(ConstraintLayout layout, ArrayList<String> result, Context c) {
      TextView output = new TextView(c);
      output.setLayoutParams(new ConstraintLayout.LayoutParams(
              ConstraintLayout.LayoutParams.MATCH_PARENT,
              ConstraintLayout.LayoutParams.WRAP_CONTENT
      ));
      output.setTextSize(25);
      output.setTextColor(Color.WHITE);
      output.setTypeface(ResourcesCompat.getFont(c, R.font.cabin_sketch_bold));

      StringBuilder temp = new StringBuilder();
      for (String line : result) {
          temp.append(line).append("\n");
      }

      output.setText(temp.toString());

      // Add the TextView to the layout
      layout.addView(output);
      return temp.toString();
  }

//    public static String displayResult(ConstraintLayout layout, ArrayList<String> result, Context c) {
//        TextView output = new TextView(c);
//        LinearLayout linearLayout = new LinearLayout(c);
//        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        ));
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//
//        output.setTextSize(20);
//        output.setTextColor(Color.WHITE);
//        String temp = null;
//        for (int i = 0; i < result.size(); i++) {
//            temp = temp + result.get(i) + "\n";
//
//        }
//        output.setText(temp);
//
//        layout.addView(linearLayout);
//        return temp;
//    }

    public static String calculateByPercent(ConstraintLayout layout, float total, Context c) {
        LinearLayout peopleLayout = (LinearLayout) layout.getChildAt(0);
        int peopleCount = peopleLayout.getChildCount();
        float sum = 0;
        ArrayList<Float> percent = new ArrayList<>();
        ArrayList<String> display = new ArrayList<>();

        for (int i = 0; i < peopleCount; i++) {
            View childView = peopleLayout.getChildAt(i);
            if (childView instanceof EditText) {
                EditText person = (EditText) childView;
                String personString = person.getText().toString().trim();
                float personPercent = Float.parseFloat(personString);


                // Check
                sum = sum + personPercent;
                percent.add(personPercent);
            }
        }

            // if sum = 100% then treat as percentage, else treat as ratio
            float result;
            String output;


            if (sum == total) {
                for (int i = 0; i < percent.size(); i++) {
                    result = percent.get(i) / 100 * total;
                    output = String.format("Person %d pay $%.2f"+" ("+percent.get(i)+")", i+1, result);
                    display.add(output);
                }
            } else {
                for (int i = 0; i < percent.size(); i++) {
                    result = percent.get(i) / sum * total;
                    output = String.format("Person %d pay $%.2f"+" ("+percent.get(i) *100/sum+")", i+1, result);
                    display.add(output);
                }
            }

            // Display
            layout.removeAllViews();
            output = displayResult(layout, display, c);
            return output;
        }

        public static String calculateByAmount (ConstraintLayout layout,float total, Context c){
            LinearLayout peopleLayout = (LinearLayout) layout.getChildAt(0);
            int peopleCount = peopleLayout.getChildCount();
            float sum = 0;
            ArrayList<Float> amount = new ArrayList<>();
            ArrayList<String> display = new ArrayList<>();

            for (int i = 0; i < peopleCount; i++) {
                View childView = peopleLayout.getChildAt(i);
                if (childView instanceof EditText) {
                    EditText person = (EditText) childView;
                    String personString = person.getText().toString().trim();
                    float personAmount = Float.parseFloat(personString);

                    // Check
                    sum = sum + personAmount;
                    amount.add(personAmount);
                }
            }

                // if sum = 100% then treat as percentage, else treat as ratio
                String output = "";

                if (sum == total) {
                    for (int i = 0; i < amount.size(); i++) {
                        output = String.format("Person %d pay $%.2f", i+1, amount.get(i));
                        display.add(output);

                        layout.removeAllViews();
                        output = displayResult(layout, display, c);
                    }
                } else {
                    showInputError(c);
                    output = "";
                }
                return output;
            }


            public static void shareResult (String result, Context c){
                // Share the result via Message or Email
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, result);

                Intent chooserIntent = Intent.createChooser(shareIntent, "Share Result via...");

                if (chooserIntent.resolveActivity(c.getPackageManager()) != null) {
                    c.startActivity(chooserIntent);
                } else {
                    // Handle the case where no suitable app is available to handle the share action.
                    Toast.makeText(c, "No suitable app available to share the result.", Toast.LENGTH_SHORT).show();
                }
            }

        }


