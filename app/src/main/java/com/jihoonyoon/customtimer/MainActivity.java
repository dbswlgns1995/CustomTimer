package com.jihoonyoon.customtimer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.jihoonyoon.customtimer.Timer.TimerActivity;
import com.jihoonyoon.customtimer.ui.dashboard.DashboardFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    DashboardFragment dashboardFragment;
    public BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        // 타이머가 작동중이면 timerActivity 로 이동
        SharedPreferences sp = getSharedPreferences("pref", 0);
        Long sharedTime = sp.getLong("SharedTime", 0);
        if(sharedTime > 0){
            Intent intent = new Intent(this, TimerActivity.class);
            startActivity(intent);
        }
    }

}