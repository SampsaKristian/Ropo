package com.example.a305.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class tempActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_main);
        onClickListenerButton();
    }
    public void onClickListenerButton(){


        Intent temp = getIntent();




    }
}