package com.example.gazadiscounts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Splach extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_splach);
    }
}
