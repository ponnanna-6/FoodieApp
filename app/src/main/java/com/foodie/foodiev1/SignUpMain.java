package com.foodie.foodiev1;

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

public class SignUpMain extends AppCompatActivity {

    EditText signUpName;
    EditText signUpEmail;
    EditText signUpPassword;
    EditText signUpConfirmPassword;
    AppCompatButton signUpButton;
    TextView alreadyHaveAcLogin;
    AppCompatButton signUpWithGoogleButton;

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
                final String result = signUpName.getText().toString();
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

            }
        });


    }
    private void hideKeyboard (View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }
}