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
    RangeSlider priceRangeSlider;
    TextView priceSelectedTextView;
    Button breakfastButton, lunchButton, dinnerButton, snacksButton, brunchButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodie_decision);

        //find views

        //veg and non-veg buttons
        vegSelectorButton=findViewById(R.id.foodieDecisionVeg);
        nonVegSelectorButton=findViewById(R.id.foodieDecisionNonVeg);

        //range slider to set price
        priceRangeSlider=findViewById(R.id.rangeSlider);
        priceSelectedTextView=findViewById(R.id.priceSelectedTextView);

        //5 meals view id
        breakfastButton=findViewById(R.id.breakfastButton);
        lunchButton=findViewById(R.id.lunchButton);
        dinnerButton=findViewById(R.id.dinnerButton);
        brunchButton=findViewById(R.id.brunchButton);
        snacksButton=findViewById(R.id.snacksButton);


        List<Float> selectedValues = priceRangeSlider.getValues();
        float minValue = selectedValues.get(0);

        priceRangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                priceSelectedTextView.setText(String.valueOf((int) value));
                //Toast.makeText(getApplicationContext(), String.valueOf(value), Toast.LENGTH_SHORT).show();
            }
        });



    }
}