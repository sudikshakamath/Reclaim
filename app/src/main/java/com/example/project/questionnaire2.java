package com.example.project;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class questionnaire2 extends AppCompatActivity implements View.OnClickListener {
    Button A, B, C, D, submitbttn;
    TextView question;
    String ans = "";
    String fin = "";
    int curr = 0;
    ArrayList <String> choices = new ArrayList<>();
    FirebaseUser mUser;
    DatabaseReference mDatabase,userRef;

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire2);
        question=findViewById(R.id.tagline);
        A=findViewById(R.id.optionA);
        B=findViewById(R.id.optionB);
        C=findViewById(R.id.optionC);
        D=findViewById(R.id.optionD);
        image=findViewById(R.id.view_image);
        submitbttn=findViewById(R.id.submit);
        A.setOnClickListener(this);
        B.setOnClickListener(this);
        C.setOnClickListener(this);
        D.setOnClickListener(this);
        submitbttn.setOnClickListener(this);
        loadNewQuestion();


        mDatabase = FirebaseDatabase.getInstance().getReference("questions");
        userRef = FirebaseDatabase.getInstance().getReference("users");
        }

    @Override
    public void onClick(View view) {
        Button clicked=(Button) view;
        if(clicked.getId()==R.id.submit){
            fin+=ans;
            choices.add(ans);
            curr++;
            loadNewQuestion();
        }
        else{
            ans=clicked.getText().toString();
        }
    }
    void loadNewQuestion(){
        if(curr==3){
            finishQuiz();
            return;
        }
        if(curr==1){
            image.setImageResource(R.drawable.beans);
        }
        if(curr==2){
            image.setImageResource(R.drawable.smoking);
        }
        question.setText(questionnaire_qa.questions[curr]);
        A.setText(questionnaire_qa.options[curr][0]);
        B.setText(questionnaire_qa.options[curr][1]);
        C.setText(questionnaire_qa.options[curr][2]);
        D.setText(questionnaire_qa.options[curr][3]);
        submitbttn.setText(questionnaire_qa.options[curr][4]);
    }
    void finishQuiz(){
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = mUser.getUid();
        String curr_freq = choices.get(0);
        String target_freq = choices.get(1);
        String time_period = choices.get(2);
        mDatabase.child(uid).child("choices").child("curr_freq").setValue(curr_freq);
        mDatabase.child(uid).child("choices").child("target_freq").setValue(target_freq);
        mDatabase.child(uid).child("choices").child("time_period").setValue(time_period);
        int months = Integer.parseInt(time_period.substring(0, 1));
        int daysLeftValue = months * 30;
        userRef.child(uid).child("days_left").setValue(daysLeftValue);
        userRef.child(uid).child("days_sober").setValue(0);

        new AlertDialog.Builder(this)
                .setTitle("Welcome dear user!")
                .setMessage("We are so excited to meet you. Now that we have all the information we need, you just have to sit back and relax.")
                .setNeutralButton("Lets begin!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(questionnaire2.this, MainActivity3.class);
                        startActivity(intent);
                    }
                })
                .setCancelable(true)
                .show();
    }
}