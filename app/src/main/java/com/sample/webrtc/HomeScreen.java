package com.sample.webrtc;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class HomeScreen extends AppCompatActivity {
    private static final String TAG = HomeScreen.class.getSimpleName();
    private static final int PERMISSION_REQUEST_CODE = 200;

    private Button button;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.main_activity);

        sharedPreferences = getSharedPreferences("webRtcAndroid", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        addListenerOnButton();
    }

    public void addListenerOnButton() {

        button = findViewById(R.id.connectBtn);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                boolean status = sharedPreferences.getBoolean("isAppFirst", false);
                if (!status) {
                    requestPermission();
                } else {
                    openHomeScreen();
                }
            }

        });

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA, RECORD_AUDIO, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    editor.putBoolean("isAppFirst", true);
                    editor.commit();
                    openHomeScreen();
                }
                break;
        }
    }

    private void openHomeScreen(){
        Intent myIntent = new Intent(HomeScreen.this, ConferenceScreen.class);
        startActivity(myIntent);
    }
}