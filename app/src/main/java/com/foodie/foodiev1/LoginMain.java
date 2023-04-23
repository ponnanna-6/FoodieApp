package com.foodie.foodiev1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
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

import com.google.firebase.auth.FirebaseAuth;

public class LoginMain extends AppCompatActivity {
    EditText EmailAddressEditText;
    EditText PasswordEditText;
    Button LoginButton;
    TextView ForgotPassword;
    Button LoginWithGoogle;
    TextView DontHaveAcSignUp;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        //Find all the views
        EmailAddressEditText=findViewById(R.id.LoginEmailAddressMain);
        PasswordEditText=findViewById(R.id.LoginPasswordMain);
        LoginButton=findViewById(R.id.LoginButtonMain);
        ForgotPassword=findViewById(R.id.forgotPasswordTextView);
        LoginWithGoogle=findViewById(R.id.loginWithGoogle);
        DontHaveAcSignUp=findViewById(R.id.dontHaveAcSignUp);


        //change color of sign up in don't have an account sign up
        String dontHaveAcString=getString(R.string.dont_have_ac_sign_up);
        SpannableString spannableString = new SpannableString(dontHaveAcString);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        spannableString.setSpan(foregroundColorSpan, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ClickableSpan signUpClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMain.this, SignUpMain.class);
                startActivity(i);
            }
        };
        spannableString.setSpan(signUpClickableSpan, dontHaveAcString.indexOf("Sign up"), dontHaveAcString.indexOf("Sign up") + "Sign up".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        DontHaveAcSignUp.setText(spannableString);
        DontHaveAcSignUp.setMovementMethod(LinkMovementMethod.getInstance());
        //change color of sign up in don't have an account sign up


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeybaord(view);
                final String result = EmailAddressEditText.getText().toString();
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

            }
        });


    }
    private void hideKeybaord (View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    //Code to navigate back to parent activity
    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }
}