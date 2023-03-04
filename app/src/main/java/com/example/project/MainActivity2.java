package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity2 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button signupbtn ;
    private EditText name ;
    private EditText email ;
    private EditText user ;
    private EditText pass ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth = FirebaseAuth.getInstance();

        signupbtn = (Button) findViewById(R.id.signupButton);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, questionnaire.class);
                startActivity(intent);
//                registerNewUser();
            }
        });
    }
//    private void registerNewUser() {
//        String email1, pass1;
//        email1 = email.getText().toString();
//        pass1 = pass.getText().toString();
//        if (TextUtils.isEmpty(email1)) {
//            Toast.makeText(getApplicationContext(),
//                    "Please enter email!!",
//                    Toast.LENGTH_LONG).show();
//            return;
//        }
//        if (TextUtils.isEmpty(pass1)) {
//            Toast.makeText(getApplicationContext(),
//                    "Please enter password!!",
//                    Toast.LENGTH_LONG).show();
//            return;
//        }
//        mAuth.createUserWithEmailAndPassword(email1, pass1)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(),
//                                    "Registration successful!",
//                                    Toast.LENGTH_LONG).show();
//
//                            // if the user created intent to login activity
//                            Intent intent
//                                    = new Intent(MainActivity2.this,
//                                    questionnaire.class);
//                            startActivity(intent);
//                        } else {
//
//                            // Registration failed
//                            Toast.makeText(
//                                            getApplicationContext(),
//                                            "Registration failed!!"
//                                                    + " Please try again later",
//                                            Toast.LENGTH_LONG)
//                                    .show();
//
//                        }
//                    }
//                });
//    }
}