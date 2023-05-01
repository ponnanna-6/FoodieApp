package com.foodie.foodiev1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.RangeSlider;

import java.util.List;

public class FoodieDecision extends AppCompatActivity {

    Button vegSelectorButton, nonVegSelectorButton;
    Button priceEconomyButton, priceMidrangeButton, priceExpensiveButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodie_decision);

        //find views

        //veg and non-veg buttons
        vegSelectorButton=findViewById(R.id.foodieDecisionVeg);
        nonVegSelectorButton=findViewById(R.id.foodieDecisionNonVeg);

    }
    @Override
    public void onBackPressed() {
        finish();
    }
}