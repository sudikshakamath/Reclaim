package com.example.project;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

public class CursiveTextAnimation extends Animation {
    private TextView textView;
    private String text;

    public CursiveTextAnimation(TextView textView, String text) {
        this.textView = textView;
        this.text = text;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int textLength = text.length();
        int index = (int) (textLength * interpolatedTime);
        if (index <= textLength) {
            textView.setText(text.substring(0, index));
        }
    }
}

