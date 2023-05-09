package com.example.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MedicationAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Display a toast message when the alarm is triggered
        Toast.makeText(context, "It's time to take your medication!", Toast.LENGTH_SHORT).show();
    }
}

