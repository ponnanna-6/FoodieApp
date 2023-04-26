package com.foodie.foodiev1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpMain extends AppCompatActivity {

    EditText signUpName;
    EditText signUpEmail;
    EditText signUpPassword;
    EditText signUpConfirmPassword;
    AppCompatButton signUpButton;
    TextView alreadyHaveAcLogin;
    AppCompatButton signUpWithGoogleButton;

    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_main);

        signUpName=findViewById(R.id.signUpName);
        signUpEmail=findViewById(R.id.signUpEmailAddress);
        signUpPassword=findViewById(R.id.signUpPassword);
        signUpConfirmPassword=findViewById(R.id.signUpConfirmPassword);
        signUpButton=findViewById(R.id.signUpButton);
        alreadyHaveAcLogin=findViewById(R.id.alreadyHaveAcTextView);
        signUpWithGoogleButton=findViewById(R.id.signUpWithGoogleButton);

        //change color of login in already have an account login
        String alreadyHaveAcString=getString(R.string.already_have_ac_login);
        SpannableString spannableString = new SpannableString(alreadyHaveAcString);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        spannableString.setSpan(foregroundColorSpan, 25, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ClickableSpan loginClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpMain.this, LoginMain.class);
                startActivity(i);
            }
        };
        spannableString.setSpan(loginClickableSpan, alreadyHaveAcString.indexOf("Login"), alreadyHaveAcString.indexOf("Login") + "Login".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        alreadyHaveAcLogin.setText(spannableString);
        alreadyHaveAcLogin.setMovementMethod(LinkMovementMethod.getInstance());
        //change color of sign up in don't have an account sign up


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                String userName = signUpName.getText().toString();
                String userEmail = signUpEmail.getText().toString();
                String userPassword = signUpPassword.getText().toString();
                String userConfirmPassword = signUpConfirmPassword.getText().toString();
                if(!userPassword.equals(userConfirmPassword)){
                    Toast.makeText(getApplicationContext(),
                            "The passwords you entered don't match. Please try again.", Toast.LENGTH_LONG).show();
                }else{
                    signUpFirebase(userEmail, userPassword);
                }


            }
        });


    }
    private void hideKeyboard (View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    public void signUpFirebase(String userEmail, String userPassword){
        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignUpMain.this,
                                    "User created successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpMain.this, LoginMain.class);
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpMain.this,
                                    "ERROR: User could not be created", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
}