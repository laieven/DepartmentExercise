package com.lbj.viewexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        ChargeView chargeView = findViewById(R.id.charge_view);
        chargeView.update(true, true);
    }

}