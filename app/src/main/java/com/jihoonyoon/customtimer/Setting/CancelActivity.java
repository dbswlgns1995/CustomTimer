package com.jihoonyoon.customtimer.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.jihoonyoon.customtimer.R;

public class CancelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);

    }

    // Notification 타이머 종료 후 어플 종료

    @Override
    protected void onResume() {

        SharedPreferences sharedPreferences = getSharedPreferences("pref", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("SharedTime", 0);
        editor.putLong("onPause", 1);
        editor.commit();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1);
        moveTaskToBack(true);
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());

        super.onResume();
    }
}
