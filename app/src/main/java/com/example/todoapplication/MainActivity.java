package com.example.todoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity
{
    private EditText user_name, pass_word;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        user_name = findViewById(R.id.email);
        pass_word = findViewById(R.id.password);

        Button btn_login = findViewById(R.id.LoginBtn);
        Button btn_register = findViewById(R.id.RegisterBtn);

        mAuth = FirebaseAuth.getInstance();

        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth)
            {
                if (firebaseAuth.getCurrentUser() != null)
                {
                    Log.d("TAG", "onComplete: " + firebaseAuth.getCurrentUser().getUid());
                    //Check if users role is Driver
                    db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task)
                        {
                            if (task.isSuccessful())
                            {
                                for (QueryDocumentSnapshot document : task.getResult())
                                {
                                    if(document.getId().matches(firebaseAuth.getCurrentUser().getUid()))
                                    {
                                        Intent it = new Intent(MainActivity.this, Dashboard.class);
                                        startActivity(it);
                                    }
                                }
                            }
                            else
                            {
                                //Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
                }
                else
                {

                }
            }
        });

        btn_login.setOnClickListener(v ->
        {
            String email = user_name.getText().toString().trim();
            String password = pass_word.getText().toString().trim();
            if (email.isEmpty())
            {
                user_name.setError("Email is empty");
                user_name.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                user_name.setError("Enter the valid email");
                user_name.requestFocus();
                return;
            }
            if (password.isEmpty())
            {
                pass_word.setError("Password is empty");
                pass_word.requestFocus();
                return;
            }
            if (password.length() < 6)
            {
                pass_word.setError("Length of password is more than 6");
                pass_word.requestFocus();
                return;
            }
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task ->
            {
                if (task.isSuccessful())
                {
                     Intent it = new Intent(MainActivity.this, Dashboard.class);
                     startActivity(it);


            };
        });

    });
        btn_register.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Register.class)));

    }
}