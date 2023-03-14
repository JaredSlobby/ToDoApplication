package com.example.todoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Dashboard extends AppCompatActivity
{
    NavigationBarView navigationBarView;

    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        navigationBarView = findViewById(R.id.bottom_navigation);

        defaultFragment();

        navBar();


    }

    private void replaceFragment(Home Home)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, Home);
        fragmentTransaction.commit();
    }
    private void replaceFragment(ToDo ToDo)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, ToDo);
        fragmentTransaction.commit();

        //bundle
        Bundle bundle = new Bundle();
        bundle.putString("status", status);
        ToDo.setArguments(bundle);
    }



    private void defaultFragment()
    {
        //Set default fragment to load
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new Home());
        fragmentTransaction.commit();
    }



    private void navBar()
    {
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.Dashboard:
                        replaceFragment(new Home());
                        return true;
                    case R.id.Todo:
                        status = "ToDo";
                        replaceFragment(new ToDo());
                        return true;
                    case R.id.Active:
                        status = "Active";
                        replaceFragment(new ToDo());
                        return true;
                    case R.id.Completed:
                        status = "Completed";
                        replaceFragment(new ToDo());
                        return true;
                }
                return false;
            }

        });
    }





}