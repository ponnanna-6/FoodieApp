package com.foodie.foodiev1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ProfileActivity extends AppCompatActivity {
    ImageView profileImageView;
    EditText profileNameEditText;
    EditText profileEmailAddressEditText;
    Button signOutButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Find the views
        profileImageView=findViewById(R.id.profileImageView);
        profileEmailAddressEditText=findViewById(R.id.profileEmailAddress);
        profileNameEditText=findViewById(R.id.profileName);
        signOutButton=findViewById(R.id.signOutButton);


        String profileNameString="Ponnanna";
        String emailAddressString="ponnanna2001@gmail.com";

        profileNameEditText.setText(profileNameString);
        profileEmailAddressEditText.setText(emailAddressString);


    }
}