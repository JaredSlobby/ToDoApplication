package com.example.todoapplication;

import android.app.DatePickerDialog;
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
import android.widget.Button;
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
import java.util.Collections;
import java.util.Comparator;


public class ToDo extends Fragment
{
    FirebaseUser user;
    String uid;
    View view;
    String TAG = "ToDo";
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapterDescription;
    ListView printTasks;


    public ToDo()
    {
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
        ArrayList<String> taskTitle = new ArrayList<>();
        ArrayList<String> completionDate = new ArrayList<>();
        ArrayList<String> description = new ArrayList<>();
        ArrayList<String> day = new ArrayList<>();
        ArrayList<String> month = new ArrayList<>();
        ArrayList<String> year = new ArrayList<>();
        ArrayList<String> dayOfWeek = new ArrayList<>();
        ArrayList<String> priority = new ArrayList<>();
        ArrayList<String> statusTask = new ArrayList<>();
        ArrayList<String> endDate = new ArrayList<>();

        printTasks = view.findViewById(R.id.tasksList);
        printTasks.setDividerHeight(0);

        ArrayList<ArrayLists> listItems = new ArrayList<>();

        CustomAdapter customAdapter = new CustomAdapter(view.getContext(), R.layout.lists, listItems);
        printTasks.setAdapter(customAdapter);

        Comparator<ArrayLists> taskComparator = new Comparator<ArrayLists>() {
            @Override
            public int compare(ArrayLists task1, ArrayLists task2)
            {
                int result = task1.getPriority().compareTo(task2.getPriority());

                String priority1 = task1.getPriority();
                String priority2 = task2.getPriority();

                if (priority1.equals(priority2)) {
                    return task1.getDueDate().compareTo(task2.getDueDate());
                } else if (priority1.equals("High")) {
                    return -1;
                } else if (priority2.equals("High")) {
                    return 1;
                } else if (priority1.equals("Medium")) {
                    return -1;
                } else if (priority2.equals("Medium")) {
                    return 1;
                }
                else if (result != 0)
                {
                    result = task1.getDueDate().compareTo(task2.getDueDate());
                    return result;
                }
                else
                {
                    return 0;
                }

            }
        };




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
                            day.add(document.getString("day"));
                            month.add(document.getString("month"));
                            dayOfWeek.add(document.getString("dayOfWeek"));
                            year.add(document.getString("year"));
                            priority.add(document.getString("Priority"));
                            statusTask.add(document.getString("Status"));
                            Log.d(TAG, "onComplete: " + document.getString("Title"));
                        }
                    }



                    for(int i = 0; i < taskTitle.size(); i++)
                    {
                        ArrayLists arrayList = new ArrayLists(taskTitle.get(i), description.get(i), day.get(i), priority.get(i), dayOfWeek.get(i),month.get(i), endDate.get(i));
                        listItems.add(arrayList);

                    }
                    listItems.sort(taskComparator);




                    customAdapter.notifyDataSetChanged();

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
                InfoBundle.putString("day", day.get(position));
                InfoBundle.putString("month", month.get(position));
                InfoBundle.putString("year", year.get(position));
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