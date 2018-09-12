package com.homework.kleanthis.salarycounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.homework.kleanthis.salarycounter.R.layout.list_item;

public class MainActivity extends AppCompatActivity {
    private static final String CHILD = "workDay";
    private static final String DAY = "day";
    private static final String STARTING_TIME = "starting-time";
    private static final String END_TIME = "end-time";
    private static final String CASH = "cash-per-hour";
    private DatabaseReference firebase;
    private EditText date, day, startingTime, endTime, cash;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebase = FirebaseDatabase.getInstance().getReference();
        date = findViewById(R.id.date);
        day = findViewById(R.id.day);
        startingTime = findViewById(R.id.startingTime);
        endTime = findViewById(R.id.endTime);
        cash = findViewById(R.id.cashPerHour);
        listView = findViewById(R.id.list_view);

        castAdapter();
    }

    public void addButtonOnClick(View view){

        //Model model = new Model(day.getText().toString(), startingTime.getText(), endTime.getText(), cash.getText());

        // write to database
        firebase.child(date.getText().toString()).child(DAY).setValue(day.getText().toString());
        firebase.child(date.getText().toString()).child(STARTING_TIME).setValue(Integer.valueOf(startingTime.getText().toString()));
        firebase.child(date.getText().toString()).child(END_TIME).setValue(Integer.valueOf(endTime.getText().toString()));
        firebase.child(date.getText().toString()).child(CASH).setValue(Double.valueOf(cash.getText().toString()));



    }

    public void castAdapter(){

        final ArrayList<String> list = new ArrayList<>();
        list.add("test1");
        list.add("test222");

        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot item: dataSnapshot.getChildren()){

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ListAdapter listAdapter = new ArrayAdapter(this, R.layout.list_item, R.id.list_day, list);

        listView.setAdapter(listAdapter);
    }
}
