package com.example.project;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.project.databinding.ActivityMain3Binding;
import com.google.android.material.bottomnavigation.BottomNavigationView;




public class MainActivity3 extends AppCompatActivity {

    ActivityMain3Binding binding;
//    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        //bottomNavigationView= (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.Alternative:
                    replaceFragment(new AlternativeFragment());
                    break;
                case R.id.alert:
                    replaceFragment(new AlertFragment());
                    break;
                case R.id.Tips:
                    replaceFragment(new TipsFragment());
                    break;
                case R.id.Account:
                    replaceFragment(new AccountFragment());
                    break;
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}