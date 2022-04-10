package com.import_mag.importmag.Activities;

import android.os.AsyncTask;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;


import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.import_mag.importmag.MainActivity;
import com.import_mag.importmag.R;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

       // HANDLER QUE PERMITE UN TIEMPO DE EJECUCION EN DETERMINADA ACTIVIDAD
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class );
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}