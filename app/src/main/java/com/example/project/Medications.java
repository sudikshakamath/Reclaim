package com.example.project;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Medications {
    private String name;
    private Calendar time;


    public Medications() {
        // Required empty constructor for Firebase
    }
    public Medications(String name, Calendar time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public Calendar getTime() {
        return time;
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String formattedTime = format.format(time.getTime());
        return name + " - " + formattedTime;
    }
}

