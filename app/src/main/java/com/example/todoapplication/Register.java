package com.example.todoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity
{
    Button RegisterButton;
    EditText user_name, pass_word,txtName;
    FirebaseAuth mAuth;

    FirebaseUser user;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        user_name = findViewById(R.id.email);
        pass_word = findViewById(R.id.password);
        txtName = findViewById(R.id.name);

        RegisterButton = findViewById(R.id.RegisterBtn);

        mAuth = FirebaseAuth.getInstance();

        RegisterButton.setOnClickListener(new View.OnClickListener()
        {
            //Validation

            @Override
            public void onClick(View v)
            {
                String name = txtName.getText().toString().trim();
                String email = user_name.getText().toString().trim();
                String password = pass_word.getText().toString().trim();

                if (name.isEmpty())
                {
                    txtName.setError("Name is empty");
                    txtName.requestFocus();
                    return;
                }
                if (email.isEmpty())
                {
                    user_name.setError("Email is empty");
                    user_name.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    user_name.setError("Enter the valid email address");
                    user_name.requestFocus();
                    return;
                }
                if (password.isEmpty())
                {
                    pass_word.setError("Enter the password");
                    pass_word.requestFocus();
                    return;
                }
                if (password.length() < 6)
                {
                    pass_word.setError("Length of the password should be more than 6");
                    pass_word.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {

                            user = FirebaseAuth.getInstance().getCurrentUser();
                            uid = user.getUid();

                            Map<String, Object> user = new HashMap<>();
                            user.put("UserName", txtName.getText().toString());
                            user.put("UID", uid);

                            db.collection("Users").document(uid).set(user).addOnSuccessListener(new OnSuccessListener<Void>()
                                    {
                                        @Override
                                        public void onSuccess(Void aVoid)
                                        {
                                            //Log.d(TAG, "DocumentSnapshot added with ID:" );
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener()
                                    {
                                        @Override
                                        public void onFailure(@NonNull Exception e)
                                        {
                                            //Log.w(TAG, "Error adding document", e);
                                        }
                                    });

                            Log.d("TAG","Testing UID Print on Register:" + uid);

                            Toast.makeText(Register.this, "You are successfully Registered", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(Register.this, "You are not Registered! Try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}