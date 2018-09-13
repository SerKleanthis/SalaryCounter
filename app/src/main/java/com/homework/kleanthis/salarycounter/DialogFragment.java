package com.homework.kleanthis.salarycounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class DialogFragment extends AppCompatActivity {

    private EditText date, day, startingTime, endTime, cash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_fragment);

        date = findViewById(R.id.date);
        day = findViewById(R.id.day);
        startingTime = findViewById(R.id.startingTime);
        endTime = findViewById(R.id.endTime);
        cash = findViewById(R.id.cashPerHour);
    }
}
