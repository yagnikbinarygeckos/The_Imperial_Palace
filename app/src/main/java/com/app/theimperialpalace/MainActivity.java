package com.app.theimperialpalace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.app.theimperialpalace.Utils.SharedPrefsUtils;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Log.w("ravi_testing_token", SharedPrefsUtils.getSharedPreferenceString(getApplicationContext(),"token_new"));
        init();
        OpenLoginScreen();
    }
    private void init() {

        imageView = findViewById(R.id.imageView);

    }
    private void OpenLoginScreen() {


        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);


                }
            }
        };thread.start();
    }
}