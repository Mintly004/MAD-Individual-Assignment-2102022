package com.example.madassignment;

import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class customBreakdownPercentage extends AppCompatActivity {

    float totalAmount = 0;
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_breakdown_percentage);

        EditText total = findViewById(R.id.totalAmount2);
        EditText people = findViewById(R.id.noOfPeople2);
        Button sbp = findViewById(R.id.submitButton2);
        ConstraintLayout containerLayout = findViewById(R.id.emptyLayout2);
        Button cbp = findViewById(R.id.calculateButton2);
        ImageButton share=findViewById(R.id.shareButton2);


        sbp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String totalString = total.getText().toString().trim();
                String peopleString = people.getText().toString().trim();

                totalAmount = Float.parseFloat(totalString);
                int noOfPeople = Integer.parseInt(peopleString);

                if (totalAmount > 0 && noOfPeople > 0) {
                    MainActivity.generateEditText(noOfPeople, containerLayout,customBreakdownPercentage.this);
                    total.setVisibility(View.GONE);
                    people.setVisibility(View.GONE);
                    sbp.setVisibility(View.GONE);
                    containerLayout.setVisibility(View.VISIBLE);
                    cbp.setVisibility(View.VISIBLE);
                } else MainActivity.showInputError(customBreakdownPercentage.this);
            }
        });

        cbp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result=MainActivity.calculateByPercent(containerLayout, totalAmount,customBreakdownPercentage.this);
                cbp.setVisibility(View.GONE);
                share.setVisibility(View.VISIBLE);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.shareResult(result,customBreakdownPercentage.this);

            }
        });

    }


}