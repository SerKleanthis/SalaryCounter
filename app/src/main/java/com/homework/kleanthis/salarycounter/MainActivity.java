package com.homework.kleanthis.salarycounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String CHILD = "workDay";
    private static final String USER_NAME = "Seretis-Kleanthis";
    private static final String DATE = "date";
    private static final String DAY = "day";
    private static final String STARTING_TIME = "starting-time";
    private static final String END_TIME = "end-time";
    private static final String CASH = "cash-per-hour";
    private DatabaseReference userFireBase;
    private RecyclerView recyclerView;
    private RecyclerViewerAdapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userFireBase = FirebaseDatabase.getInstance().getReference(USER_NAME);

        recyclerView = findViewById(R.id.recyclerView);

        castAdapter();
    }

    public void addButtonOnClick(View view){

        //Model model = new Model(day.getText().toString(), startingTime.getText(), endTime.getText(), cash.getText());

       /* // write to database
        userFireBase.child(date.getText().toString()).child(DAY).setValue(day.getText().toString());
        userFireBase.child(date.getText().toString()).child(STARTING_TIME).setValue(Integer.valueOf(startingTime.getText().toString()));
        userFireBase.child(date.getText().toString()).child(END_TIME).setValue(Integer.valueOf(endTime.getText().toString()));
        userFireBase.child(date.getText().toString()).child(CASH).setValue(Double.valueOf(cash.getText().toString()));
*/


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
        model.setStartingTime(dataSnapshot.child(STARTING_TIME).getValue(Integer.class));
        model.setEndTime(dataSnapshot.child(END_TIME).getValue(Integer.class));
        model.setCash(dataSnapshot.child(CASH).getValue(Double.class));

        return model;
    }
}
