package com.example.ecommerciandroiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.FeatureGroupInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private TextView textView8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar2);
        CountDownTimer countDownTimer = new CountDownTimer(3000, 900) {
            @Override
            public void onTick(long millisUntilFinished) {
                int current = progressBar.getProgress();
                if(current >= progressBar.getMax()){
                    current=0;
                }
                progressBar.setProgress(current + 40);

            }

            @Override
            public void onFinish() {
//                SystemClock.sleep(3000);
                /*Intent registerIntent = new Intent(SplashActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                finish();*/
            }

        };
         countDownTimer.start();

    }

@Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser == null) {
            Intent registerIntent = new Intent(SplashActivity.this,RegisterActivity.class);
            startActivity(registerIntent);
            finish();
        }else{
            Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
    }
}
