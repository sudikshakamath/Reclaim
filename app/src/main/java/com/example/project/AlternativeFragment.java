package com.example.project;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class AlternativeFragment extends Fragment {
    private GridView gridView;
    CardView smoking,alcohol,socialmedia,drugs,caffeine,food_drink;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_alternative, container, false);
        // Inflate the layout for this fragment
        smoking=(CardView) rootView.findViewById(R.id.smokingCard);
        smoking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent= new Intent(getContext(),smokingalternatives.class);
                startActivity(myintent);
            }
        });
        alcohol=(CardView) rootView.findViewById(R.id.alcoholCard);
        alcohol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent= new Intent(getContext(),alcoholalternatives.class);
                startActivity(myintent);
            }
        });

        socialmedia=(CardView) rootView.findViewById(R.id.socialCard);
        socialmedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent= new Intent(getContext(),socialmediaalternatives.class);
                startActivity(myintent);
            }
        });
        drugs=(CardView) rootView.findViewById(R.id.drugsCard);
        drugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent= new Intent(getContext(),drugsalternatives.class);
                startActivity(myintent);
            }
        });
        food_drink=(CardView) rootView.findViewById(R.id.foodCard);
        food_drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent= new Intent(getContext(),foodalternatives.class);
                startActivity(myintent);
            }
        });
        caffeine=(CardView) rootView.findViewById(R.id.caffeineCard);
        caffeine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent= new Intent(getContext(),caffeinealternatives.class);
                startActivity(myintent);
            }
        });
        return rootView;
    }
}