package com.example.madassignment;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class equallyBreakdown extends AppCompatActivity {

String temResult;
    float result=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equally_breakdown);

        EditText total = findViewById(R.id.totalAmount1);
        EditText people = findViewById(R.id.noOfPeople1);
        Button eb = findViewById(R.id.calculateButton1);
        ImageButton share=findViewById(R.id.shareButton1);
        TextView tv = findViewById(R.id.textView);

        eb.setOnClickListener(v -> {

            String totalString = total.getText().toString().trim();
            String peopleString = people.getText().toString().trim();

            float totalAmount = Float.parseFloat(totalString);
            int noOfPeople = Integer.parseInt(peopleString);

            if (totalAmount > 0 && noOfPeople > 0) {
                result = totalAmount / noOfPeople;

                tv.setText(String.format("%.2f/pax", result));


                share.setVisibility(View.VISIBLE);


            } else MainActivity.showInputError(equallyBreakdown.this);

        });
        share.setOnClickListener(v->{
            MainActivity.shareResult(tv.getText().toString().trim(),equallyBreakdown.this);
        });


    }
}