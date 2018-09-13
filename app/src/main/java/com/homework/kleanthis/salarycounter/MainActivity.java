package com.homework.kleanthis.salarycounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.widget.Toast.*;
import static com.homework.kleanthis.salarycounter.R.layout.list_item;

public class MainActivity extends AppCompatActivity {
    private static final String CHILD = "workDay";
    private static final String DATE = "date";
    private static final String DAY = "day";
    private static final String STARTING_TIME = "starting-time";
    private static final String END_TIME = "end-time";
    private static final String CASH = "cash-per-hour";
    private DatabaseReference firebase;
    private EditText date, day, startingTime, endTime, cash;
    final Model data = new Model();
    private RecyclerView recyclerView;
    private RecyclerViewerAdapter myAdapter = new RecyclerViewerAdapter(data);

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
        recyclerView = findViewById(R.id.recyclerView);


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

        /*final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(listAdapter);*/

        // method 2

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);

        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                data.setDate(dataSnapshot.child(DATE).getValue(String.class));
                data.setDay(dataSnapshot.child(DAY).getValue(String.class));
                data.setStartingTime(dataSnapshot.child(STARTING_TIME).getValue(Long.class));
                data.setEndTime(dataSnapshot.child(END_TIME).getValue(Long.class));
                data.setCash(dataSnapshot.child(CASH).getValue(Double.class));

                cash.setText(data.getCash()+"");

                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
