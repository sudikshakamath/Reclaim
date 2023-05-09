package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.button.MaterialButton;



public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView emailTextView ;
    private TextView passwordTextView ;

    private TextView reclaim ;
    private Button loginbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        emailTextView =(TextView) findViewById(R.id.username);

        passwordTextView =(TextView) findViewById(R.id.password);
        loginbtn= (Button) findViewById(R.id.loginbtn);
        reclaim = (TextView) findViewById(R.id.reclaim);
//        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(
//                reclaim,
//                PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f)
//        );
//        animator.setStartDelay(500); // Set a delay of 500 milliseconds before starting the animation
//        animator.setDuration(5000); // Set the duration of the animation to 1 second
//        animator.start(); // Start the animation
        String text = "reclaim";
        CursiveTextAnimation animation = new CursiveTextAnimation(reclaim, text);
        animation.setDuration(4000); // Set the duration of the animation to 5 seconds
        reclaim.startAnimation(animation);




        //admin and admin
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserAccount();
            }
        });


        TextView signuptext = (TextView) findViewById(R.id.signupText);
        signuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(myintent);
            }
        });
    }
    private void loginUserAccount(){
        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        // validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                                    "Login successful!!",
                                                    Toast.LENGTH_LONG)
                                            .show();


                                    // if sign-in is successful
                                    // intent to home activity
                                    Intent intent
                                            = new Intent(MainActivity.this,
                                            MainActivity3.class);
                                    startActivity(intent);
                                }

                                else {

                                    // sign-in failed
                                    Toast.makeText(getApplicationContext(),
                                                    "Login failed!!",
                                                    Toast.LENGTH_LONG)
                                            .show();

                                }
                            }
                        });

    }
}