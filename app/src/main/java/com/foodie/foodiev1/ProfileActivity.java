package com.foodie.foodiev1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.foodie.foodiev1.authentication.LoginPageStart;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    ImageView profileImageView;
    EditText profileNameEditText;
    EditText profileEmailAddressEditText;
    Button signOutButton;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();

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

        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        String profileNameString;
        if(user.getDisplayName()!=null){
            profileNameString=user.getDisplayName();
        }else{
            profileNameString=user.getEmail();
        }

        String emailAddressString=user.getEmail();

        profileNameEditText.setText(profileNameString);
        profileEmailAddressEditText.setText(emailAddressString);

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        if (firebaseAuth.getCurrentUser() == null) {
                            Intent intent = new Intent(ProfileActivity.this, LoginPageStart.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });


    }
    @Override
    public void onBackPressed() {
        finish();
    }

}