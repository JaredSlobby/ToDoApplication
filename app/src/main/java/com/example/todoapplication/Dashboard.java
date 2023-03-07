package com.example.todoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;

public class Dashboard extends AppCompatActivity
{
    NavigationBarView navigationBarView;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Set default fragment to load
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new Home());
        fragmentTransaction.commit();


        navigationBarView = findViewById(R.id.bottom_navigation);


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




}