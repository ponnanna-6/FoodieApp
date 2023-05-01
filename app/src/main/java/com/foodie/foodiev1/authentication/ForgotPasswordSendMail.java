package com.foodie.foodiev1.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.foodie.foodiev1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordSendMail extends AppCompatActivity {

    EditText forgotPasswordEmailEditText;
    Button forgotPasswordSendButton;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_send_mail);

        forgotPasswordEmailEditText=findViewById(R.id.resetPasswordEmail);
        forgotPasswordSendButton=findViewById(R.id.resetPasswordSendButton);

        forgotPasswordSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = forgotPasswordEmailEditText.getText().toString();

                firebaseAuth.sendPasswordResetEmail(userEmail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ForgotPasswordSendMail.this,
                                            "We have sent an email to reset your password.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{

                                    Toast.makeText(ForgotPasswordSendMail.this, "User does not exist failed to send link.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }
}
