package com.example.a305.splashscreen;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    ImageButton androidImageButton;
    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isServicesOK()){
            init();
        }


        /*androidImageButton = (ImageButton) findViewById(R.id.imageButton);
        androidImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(MainActivity.this, homeActivity.class);
                startActivity(home);

            }
        });*/
        androidImageButton = (ImageButton) findViewById(R.id.imageButton2);
        androidImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent road = new Intent(MainActivity.this, roadActivity.class);
                startActivity(road);
            }
        });
        androidImageButton = (ImageButton) findViewById(R.id.imageButton3);
        androidImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent temp = new Intent(MainActivity.this, tempActivity.class);
                startActivity(temp);
            }
        });
        androidImageButton = (ImageButton) findViewById(R.id.imageButton4);
        androidImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aurora = new Intent(MainActivity.this, auroraActivity.class);
                startActivity(aurora);
            }
        });
        androidImageButton = (ImageButton) findViewById(R.id.imageButton5);
        androidImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(MainActivity.this, dataActivity.class);
                startActivity(data);
            }
        });
        androidImageButton = (ImageButton) findViewById(R.id.imageButton6);
        androidImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calendar = new Intent(MainActivity.this, calendarActivity.class);
                startActivity(calendar);
            }
        });
    }
    private void init (){

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, homeActivity.class);
                startActivity(intent);
            }
        });

    }
    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //Kaikki toimii ja on fine
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServicesOK: an error occured, can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
        }else {
            Toast.makeText(this,"You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}
