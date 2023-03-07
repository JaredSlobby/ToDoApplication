package com.example.todoapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class ToDo extends Fragment
{
    FirebaseUser user;
    String uid;
    View view;
    String TAG = "ToDo";

    ArrayAdapter<String> adapter;
    ListView printTasks;

    ArrayList<String> taskTitle;

    public ToDo() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_to_do, container, false);

        displayToDo();
        return view;
    }


    private void displayToDo()
    {

        //Pull bundle
        Bundle bundle = this.getArguments();
        String status1 = bundle.getString("status");
        String status;
        status = status1;

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        taskTitle = new ArrayList<>();


        printTasks = view.findViewById(R.id.tasksList);
        printTasks.setDividerHeight(0);


        adapter = new ArrayAdapter(view.getContext(), R.layout.lists, R.id.taskTitle, taskTitle);
        printTasks.setAdapter(adapter);

        db.collection("Tasks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        if(document.getString("UID").matches(uid) && document.getString("Status").equals(status))
                        {
                            taskTitle.add(document.getString("Title"));
                            Log.d(TAG, "onComplete: " + document.getString("Title"));
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        printTasks.setOnItemClickListener((parent, view, position, id) ->
        {
            String task = taskTitle.get(position);
            Toast.makeText(view.getContext(), task, Toast.LENGTH_SHORT).show();
        });



    }

}