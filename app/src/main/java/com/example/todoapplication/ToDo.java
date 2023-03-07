package com.example.todoapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    ArrayList<String> completionDate;
    ArrayList<String> description;
    ArrayList<String> endDate;
    ArrayList<String> priority;
    ArrayList<String> statusTask;

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
        completionDate = new ArrayList<>();
        description = new ArrayList<>();
        endDate = new ArrayList<>();
        priority = new ArrayList<>();
        statusTask = new ArrayList<>();


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
                            completionDate.add(document.getString("Completion Date"));
                            description.add(document.getString("Description"));
                            endDate.add(document.getString("End Date"));
                            priority.add(document.getString("Priority"));
                            statusTask.add(document.getString("Status"));
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

        printTasks.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Bundle InfoBundle = new Bundle();
                InfoBundle.putString("Title", taskTitle.get(position));
                InfoBundle.putString("CompletionDate", completionDate.get(position));
                InfoBundle.putString("Description", description.get(position));
                InfoBundle.putString("EndDate", endDate.get(position));
                InfoBundle.putString("Priority", priority.get(position));
                InfoBundle.putString("Status", statusTask.get(position));

                Log.d(TAG, "onItemClick TITLE: " + taskTitle.get(position));

                Fragment fragment = new TaskInfo();
                fragment.setArguments(InfoBundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });



    }

}