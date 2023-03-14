package com.example.todoapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Home extends Fragment
{
    View view;
    View bottomSheetView;

    BottomSheetDialog bottomSheetDialog;
    private DatePickerDialog datePickerDialog;
    FirebaseUser user;
    String uid;
    Button btnDatePicker;
    AutoCompleteTextView txtStatus;
    AutoCompleteTextView txtPriority;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Home()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet, bottomSheetDialog.findViewById(R.id.bottomSheet));
        CardView();


        return view;
    }



    private String makeDateString(String dayOfWeek, int day, int month, int year)
    {
        return dayOfWeek + " " + day + " " + getMonthFormat(month)  + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 0)
            return "JANUARY";
        if(month == 1)
            return "FEBRUARY";
        if(month == 2)
            return "MARCH";
        if(month == 3)
            return "APRIL";
        if(month == 4)
            return "MAY";
        if(month == 5)
            return "JUNE";
        if(month == 6)
            return "JULY";
        if(month == 7)
            return "AUGUST";
        if(month == 8)
            return "SEPTEMBER";
        if(month == 9)
            return "OCTOBER";
        if(month == 10)
            return "NOVEMBER";
        if(month == 11)
            return "DECEMBER";

        //Only if nothing else is returned
        return "JANUARY";
    }

    //onResume method
    @Override
    public void onResume()
    {
        super.onResume();
        dropDownMenus();
    }
    private void CardView()
    {
        //CardView
        CardView cardView = view.findViewById(R.id.AddTask);
        cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getTodaysDate();
                submitTask();
                showDatePickerDialog();
            }
        });
    }

    private String getTodaysDate()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        //Convert the integer to a string representing the day of the week
        
        String dayOfWeekString = "";
        
            if(dayOfWeek == 1) 
            {
                dayOfWeekString = "SUNDAY";
            }
            if(dayOfWeek == 2) 
            {
                 dayOfWeekString = "MONDAY";
            }
            if(dayOfWeek == 3) 
            {
                 dayOfWeekString = "TUESDAY";
            }
            if(dayOfWeek == 4) 
            {
                 dayOfWeekString = "WEDNESDAY";
            }
            if(dayOfWeek == 5) 
            {
                 dayOfWeekString = "THURSDAY";
            }
            if(dayOfWeek == 6) 
            {
                 dayOfWeekString = "FRIDAY";
            }
            if(dayOfWeek == 7) 
            {
                 dayOfWeekString = "SATURDAY";
            }
            

        return makeDateString(dayOfWeekString, day, month, year);
    }

    private void showDatePickerDialog()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {

                // Create a Calendar instance and set the date
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day); // year, month (0-based), day

                //get day of week from the date
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                Log.d("DAY_OF_WEEK", "onDateSet: " + dayOfWeek);

// Convert the integer to a string representing the day of the week
                String dayOfWeekString = "";

                    if(dayOfWeek == 1)
                    {
                       dayOfWeekString = "SUNDAY";
                    }
                    if(dayOfWeek == 2)
                    {
                        dayOfWeekString = "MONDAY";
                    }
                    if(dayOfWeek == 3)
                    {
                        dayOfWeekString = "TUESDAY";
                    }
                    if(dayOfWeek == 4)
                    {
                        dayOfWeekString = "WEDNESDAY";
                    }
                    if(dayOfWeek == 5)
                    {
                        dayOfWeekString = "THURSDAY";
                    }
                    if(dayOfWeek == 6)
                    {
                        dayOfWeekString = "FRIDAY";
                    }
                    if(dayOfWeek == 7)
                    {
                        dayOfWeekString = "SATURDAY";
                    }

                String date = makeDateString(dayOfWeekString, day, month, year);
                btnDatePicker.setText(date);
            }
        };



        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getActivity(), style, dateSetListener, year, month, day);
    }


    private void dropDownMenus()
    {
        txtStatus = bottomSheetView.findViewById(R.id.Status);
        txtPriority = bottomSheetView.findViewById(R.id.Priority);

        //Get string array from Strings
        String[] status = getResources().getStringArray(R.array.Status);
        ArrayAdapter<String> adapterStatus = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, status);
        txtStatus.setAdapter(adapterStatus);
        txtStatus.setThreshold(1);


        txtStatus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                txtStatus.setAdapter(adapterStatus);
            }
        });

        txtStatus.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                txtStatus.setHighlightColor(0);
            }
        });

        //Set items in autocomplete textview Priority
        String[] priority = getResources().getStringArray(R.array.Priority);
        ArrayAdapter<String> adapterPriority = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, priority);
        txtPriority.setAdapter(adapterPriority);

        txtPriority.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                txtPriority.setAdapter(adapterPriority);
            }
        });


        txtPriority.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                txtPriority.setHighlightColor(0);
            }
        });
    }

    private void submitTask()
    {
        btnDatePicker = bottomSheetView.findViewById(R.id.datePicker);

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        Button btnAddTask = bottomSheetView.findViewById(R.id.btnAddTask);
        TextInputEditText txtDescription = bottomSheetView.findViewById(R.id.Description);
        TextInputEditText txtTitle = bottomSheetView.findViewById(R.id.Title);

        btnDatePicker.setText(getTodaysDate());

        btnDatePicker.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                datePickerDialog.show();
            }
        });

        btnAddTask.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                user = FirebaseAuth.getInstance().getCurrentUser();
                uid = user.getUid();

                Map<String, Object> task = new HashMap<>();
                task.put("UID", uid);
                task.put("Priority", txtStatus.getText().toString());
                task.put("Status", txtPriority.getText().toString());
                task.put("Description", txtDescription.getText().toString());
                task.put("Title", txtTitle.getText().toString());
                task.put("Date", btnDatePicker.getText().toString());

                db.collection("Tasks").add(task).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                    {
                        Log.d("TAG", "onSuccess: Task Added" + documentReference.getId());
                    }
                });

                bottomSheetDialog.dismiss();
            }
        });
    }
}