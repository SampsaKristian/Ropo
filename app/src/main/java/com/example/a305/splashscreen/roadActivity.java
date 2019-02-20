package com.example.a305.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class roadActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.road_main);
        onClickListenerButton();
    }
    public void onClickListenerButton(){


        Intent road = getIntent();




    }
}