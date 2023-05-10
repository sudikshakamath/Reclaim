package com.example.project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment {

    private TextView quoteTextView,daysSober;
    private ProgressBar progress;
    int daysSoberValue,daysLeftValue;
    private EditText goalsEditText;
    private TextView goalsTextView;
    private List<String> goalsList;

    private String[] quotes = {
            "Don’t let the past steal your present.",
            "Courage isn’t having the strength to go on – it is going on when you have no strength.",
            "If you can quit for a day, you can quit for a lifetime.",
            "It Does Not Matter How Slowly You Go as Long As You Do Not Stop.",
            "The only person you are destined to become is the person you decide to be."
    };

    private Random random = new Random();

    private Runnable changeQuoteRunnable = new Runnable() {
        @Override
        public void run() {
            int randomIndex = random.nextInt(quotes.length);
            String randomQuote = quotes[randomIndex];
            quoteTextView.setText(randomQuote);
            quoteTextView.postDelayed(changeQuoteRunnable, 5000); // Refresh every 5 seconds
        }
    };

    private Button callButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        quoteTextView = view.findViewById(R.id.quoteTextView);
        goalsEditText = view.findViewById(R.id.goalsEditText);
        goalsTextView = view.findViewById(R.id.goalsTextView);
        Button saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGoal();
            }
        });

        callButton = view.findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCallOptionsDialog();
            }
        });

        // Initialize goalsList
        goalsList = new ArrayList<>();

        progress = (ProgressBar) view.findViewById(R.id.progressBar);
        daysSober= (TextView) view.findViewById(R.id.postsNumber);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        DatabaseReference userRef = database.getReference("users").child(uid);
        userRef.child("days_sober").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                daysSoberValue = dataSnapshot.getValue(Integer.class);
                progress.setProgress(daysSoberValue);
                daysSober.setText(String.valueOf(daysSoberValue));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });
        userRef.child("days_left").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                daysLeftValue = dataSnapshot.getValue(Integer.class);
                progress.setMax(daysLeftValue);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        quoteTextView.post(changeQuoteRunnable); // Start the quote refreshing
    }

    @Override
    public void onPause() {
        super.onPause();
        quoteTextView.removeCallbacks(changeQuoteRunnable); // Stop the quote refreshing
    }

    private void saveGoal() {
        String goal = goalsEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(goal)) {
            goalsList.add(goal);
            updateGoalsTextView();
            goalsEditText.setText("");
        } else {
            Toast.makeText(requireContext(), "Please enter a goal", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateGoalsTextView() {
        StringBuilder goalsText = new StringBuilder();
        for (String goal : goalsList) {
            goalsText.append(goal).append("\n");
        }
        goalsTextView.setText(goalsText.toString());
    }

    private void openCallOptionsDialog() {
        String[] options = {"Deaddiction Hotline", "Ambulance"};

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select an option")
                .setItems(options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                dialPhoneNumber("1234567890"); // Replace with the actual deaddiction hotline number
                                break;
                            case 1:
                                dialPhoneNumber("108"); // Replace with the actual ambulance number
                                break;
                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }
}


