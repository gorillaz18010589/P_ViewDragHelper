package com.example.p_viewdraghelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toPage2(View view) {
        startActivity(new Intent(MainActivity.this,SecondActivity.class));
    }

    public void toPage3(View view) {
        startActivity(new Intent(MainActivity.this,ThreActivity.class));
    }
}