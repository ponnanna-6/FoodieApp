package com.foodie.foodiev1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginMain extends AppCompatActivity {
    EditText EmailAddressEditText;
    EditText PasswordEditText;
    Button LoginButton;
    TextView ForgotPassword;
    Button LoginWithGoogle;
    TextView DontHaveAcSignUp;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    GoogleSignInClient googleSignInClient;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        registerActivityForGoogleSignIn();

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
                hideKeyboard(view);
                String userEmail = EmailAddressEditText.getText().toString();
                String userPassword = PasswordEditText.getText().toString();
                if(userEmail.isEmpty()){
                    Toast.makeText(LoginMain.this, "Email field is empty", Toast.LENGTH_SHORT).show();
                }else if(userPassword.isEmpty()){
                    Toast.makeText(LoginMain.this, "Password field is empty", Toast.LENGTH_SHORT).show();
                }else{
                    signInFirebase(userEmail, userPassword);
                }
            }
        });

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginMain.this, ForgotPasswordSendMail.class);
                startActivity(intent);
            }
        });


        LoginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInGoogle();
            }
        });



    }
    private void hideKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    //Code to navigate back to parent activity
    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

    public void signInFirebase(String userEmail, String userPassword){
        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginMain.this,
                                    "Logged in successfully!!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginMain.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginMain.this,
                                    "Email or Password incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signInGoogle(){
        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("31417614006-05rt5v7vqf8bp237jh2m5bv0nnqs21oc.apps.googleusercontent.com")
                .requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(LoginMain.this, gso);
        signIn();
    }

    public void signIn(){
        Intent signInIntent = googleSignInClient.getSignInIntent();
        activityResultLauncher.launch(signInIntent);
    }

    public void registerActivityForGoogleSignIn(){
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int resultCode = result.getResultCode();
                        Intent data = result.getData();

                        if(resultCode==RESULT_OK && data !=null){
                            Task<GoogleSignInAccount> task
                                    =GoogleSignIn.getSignedInAccountFromIntent(data);
                            firebaseSignInWithGoogle(task);
                        }
                    }
                });

    }

    private void firebaseSignInWithGoogle(Task<GoogleSignInAccount> task){
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            Toast.makeText(LoginMain.this,
                    "Logged in successfully!!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginMain.this, MainActivity.class);
            startActivity(intent);
            finish();
            firebaseGoogleAccount(account);
        } catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void firebaseGoogleAccount(GoogleSignInAccount account){
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                        }else{

                        }
                    }
                });

    }
}