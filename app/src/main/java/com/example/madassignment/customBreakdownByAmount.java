package com.example.madassignment;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class customBreakdownByAmount extends AppCompatActivity {

    float totalAmount = 0;
    String result = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_breakdown_by_amount);

        EditText total = findViewById(R.id.totalAmount3);
        EditText people = findViewById(R.id.noOfPeople3);
        Button sbp = findViewById(R.id.submitButton3);
        ConstraintLayout containerLayout = findViewById(R.id.emptyLayout3);
        Button cbp = findViewById(R.id.calculateButton3);
        ImageButton share=findViewById(R.id.shareButton3);

        sbp.setOnClickListener(v -> {
            String totalString = total.getText().toString().trim();
            String peopleString = people.getText().toString().trim();

            totalAmount = Float.parseFloat(totalString);
            int noOfPeople = Integer.parseInt(peopleString);

            if (totalAmount > 0 && noOfPeople > 0) {
                MainActivity.generateEditText(noOfPeople, containerLayout,customBreakdownByAmount.this);
                total.setVisibility(View.GONE);
                people.setVisibility(View.GONE);
                sbp.setVisibility(View.GONE);
                containerLayout.setVisibility(View.VISIBLE);
                cbp.setVisibility(View.VISIBLE);
            } else MainActivity.showInputError(customBreakdownByAmount.this);
        });

        cbp.setOnClickListener(v -> {
            result = MainActivity.calculateByAmount(containerLayout, totalAmount,customBreakdownByAmount.this);
            cbp.setVisibility(View.GONE);
            share.setVisibility(View.VISIBLE);
        });
        share.setOnClickListener(v->{
            MainActivity.shareResult(result,customBreakdownByAmount.this);
        });
    }
}