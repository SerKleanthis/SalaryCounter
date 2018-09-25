package com.homework.kleanthis.salarycounter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String USER_NAME = "Seretis-Kleanthis";
    private static final String DATE = "date";
    private static final String DAY = "day";
    private static final String STARTING_TIME = "starting-time";
    private static final String END_TIME = "end-time";
    private static final String CASH = "cash-per-hour";
    private TextView date, startingTime, endTime;
    private EditText cash;
    private Spinner daySpinner;
    private DatabaseReference userFireBase;
    private RecyclerView recyclerView;
    private RecyclerViewerAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userFireBase = FirebaseDatabase.getInstance().getReference(USER_NAME);

        recyclerView = findViewById(R.id.recyclerView);
        date = findViewById(R.id.date);
        daySpinner = findViewById(R.id.day);
        startingTime = findViewById(R.id.startingTime);
        endTime = findViewById(R.id.endTime);
        cash = findViewById(R.id.cashPerHour);

        String[] days = getResources().getStringArray(R.array.days_array);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(MainActivity.this,
                    android.R.layout.simple_list_item_1, days);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(spinnerAdapter);
       /* daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        daySpinner.setI
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateClickPikcer();
            }
        });

        startingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker(startingTime);
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker(endTime);
            }
        });

        castAdapter();
    }

    public void dateClickPikcer(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth +"/"+ month +"/"+ year);
                    }
                },year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    public void timePicker(final TextView textView){
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (textView == startingTime)
                    startingTime.setText(hourOfDay + ":" + minute);
                else
                    endTime.setText(hourOfDay + ":" + minute);
            }

        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void addButtonOnClick(View view){

        // TODO add values to the firebase

        if (validateFields()) {

            HashMap<String, String> dataMap = new HashMap<>();

            dataMap.put(DATE, date.getText().toString());
            dataMap.put(DAY, daySpinner.getSelectedItem().toString());
            dataMap.put(STARTING_TIME, startingTime.getText().toString());
            dataMap.put(END_TIME, endTime.getText().toString());
            dataMap.put(CASH, cash.getText().toString());

            userFireBase.push().setValue(dataMap);
        }
    }

    public boolean validateFields(){
        if(date.getText().equals("") || daySpinner.getSelectedItem().equals("") || startingTime.getText().equals("")
                        || endTime.getText().equals("") || cash.getText().equals("")) {
            Toast.makeText(getApplicationContext(), "One of required fields is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void castAdapter(){

        /*final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(listAdapter);*/

        // method 2

        final ArrayList<Model> list = new ArrayList<>();

      /*  layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new RecyclerViewerAdapter(list);
        recyclerView.setAdapter(myAdapter);*/

        myAdapter = new RecyclerViewerAdapter(list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);

        userFireBase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                list.add(fillUpModel(dataSnapshot));

                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                list.clear();

                list.add(fillUpModel(dataSnapshot));

                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public Model fillUpModel(DataSnapshot dataSnapshot){

        Model model = new Model();

       // String dateKey = dataSnapshot.getKey();

        model.setDate(dataSnapshot.child(DATE).getValue(String.class));
        model.setDay(dataSnapshot.child(DAY).getValue(String.class));
        model.setStartingTime(dataSnapshot.child(STARTING_TIME).getValue(String.class));
        model.setEndTime(dataSnapshot.child(END_TIME).getValue(String.class));
        model.setCash(dataSnapshot.child(CASH).getValue(String.class));

        return model;
    }
}
