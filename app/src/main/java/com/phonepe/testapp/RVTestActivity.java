package com.phonepe.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RVTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rvtext);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new RVTestFragment()).commit();
    }
}
