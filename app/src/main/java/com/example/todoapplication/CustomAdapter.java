package com.example.todoapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter  extends ArrayAdapter<ArrayLists>
{
    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<ArrayLists> Title)
    {
        super(context, resource, Title);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // inflate the layout for each item
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lists, parent, false);
        }

        // get the data for this position
        ArrayLists arrayList = getItem(position);

        // set the data for arrayList position
        TextView textView1 = convertView.findViewById(R.id.Title);
        textView1.setText(arrayList.getTitle());
        TextView textView2 = convertView.findViewById(R.id.TaskDescription);
        textView2.setText(arrayList.getDescription());
        TextView textView3 = convertView.findViewById(R.id.day);
        textView3.setText(arrayList.getDay());
        TextView textView4 = convertView.findViewById(R.id.Priority);
        textView4.setText(arrayList.getPriority());
        TextView textView6 = convertView.findViewById(R.id.month);
        textView6.setText(arrayList.getMonth());
        TextView textView7 = convertView.findViewById(R.id.dayOfWeek);
        textView7.setText(arrayList.getDayOfWeek());

        //Check what priority the task is and set the color of the priority textview
        CheckPriority(arrayList.getPriority(), textView4);

        return convertView;
    }

    //Check what priority the task is and set the color of the priority textview
    public void CheckPriority(String priority, TextView textView)
    {
        if (priority.equals("High"))
        {
            textView.setTextColor(getContext().getResources().getColor(R.color.red));
        }
        else if (priority.equals("Medium"))
        {
            textView.setTextColor(getContext().getResources().getColor(R.color.orange));
        }
        else if (priority.equals("Low"))
        {
            textView.setTextColor(getContext().getResources().getColor(R.color.green));
        }
    }

}
