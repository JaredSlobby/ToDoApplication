package com.example.todoapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TaskInfo extends Fragment
{
    View view;
    TextView taskInfo;
    String TAG = "TaskInfo";

    public TaskInfo()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_task_info, container, false);

        DisplayTaskInfo();

        return view;
    }

    private void DisplayTaskInfo()
    {
        Bundle bundle = this.getArguments();
        String taskTitle = bundle.getString("Title");
        String completionDate = bundle.getString("CompletionDate");
        String description = bundle.getString("Description");
        String month = bundle.getString("month");
        String priority = bundle.getString("Priority");
        String status = bundle.getString("Status");
        String day = bundle.getString("day");
        String year = bundle.getString("year");

        //taskInfo.setText("Task Title: " + taskTitle + " " + "Completion Date: " + completionDate + " " + "Description: " + description + " " + "End Date: " + endDate + " " + "Priority: " + priority + " " + "Status: " + status);
    }



}