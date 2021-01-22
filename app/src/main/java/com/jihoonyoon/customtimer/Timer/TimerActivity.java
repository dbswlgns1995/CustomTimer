package com.jihoonyoon.customtimer.Timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.jihoonyoon.customtimer.R;
import com.jihoonyoon.customtimer.Setting.CancelActivity;

import io.realm.Realm;
import io.realm.RealmResults;

public class TimerActivity extends AppCompatActivity {

    TextView textView;
    Button stop_btn, restart_btn, cancel_btn;
    long time;
    private CountDownTimer countDownTimer;
    String TAG = "Logd";
    PendingIntent mPendingIntent;
    PendingIntent cancelIntent;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        SharedPreferences sp = getSharedPreferences("pref", 0);
        Long sharedTime = sp.getLong("SharedTime", 0);
        Long onPause = sp.getLong("onPause", 1);
        int language = sp.getInt("Language", 0);

        mediaPlayer = new MediaPlayer();

        Intent intent2 = new Intent(this, TimerActivity.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mPendingIntent = PendingIntent.getActivity(this, 0, intent2, 0);

        // 타이머 취소 pendingintent
        Intent intent3 = new Intent(this, CancelActivity.class);
        intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        cancelIntent = PendingIntent.getActivity(this, 0, intent3, 0);

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Time> myTime = realm.where(Time.class).findAll();
        TimeAdapter adapter = new TimeAdapter(myTime, this);

        textView = (TextView) findViewById(R.id.timer_text);
        stop_btn = (Button) findViewById(R.id.stop_btn);
        restart_btn = (Button) findViewById(R.id.restart_btn);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);

        // 언어 설정
        if (language == 0) {
            stop_btn.setText("일시정지");
            restart_btn.setText("재시작");
            cancel_btn.setText("취소");
        } else if (language == 1) {
            stop_btn.setText("PAUSE");
            restart_btn.setText("RESTART");
            cancel_btn.setText("CANCEL");
        }


        stop_btn.setVisibility(View.VISIBLE);
        cancel_btn.setVisibility(View.VISIBLE);
        restart_btn.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        if (intent.getLongExtra("TimerTime", 0) > 0) {
            time = intent.getLongExtra("TimerTime", 0);
        }
        Intent intent1 = getIntent();
        if (intent1.getLongExtra("MyTimerTime", 0) > 0) {
            time = intent1.getLongExtra("MyTimerTime", 0);
            Log.d(TAG, "onCreate: " + time);
        }

        if (sharedTime > 0) {
            time = sharedTime;
        }


        Log.d(TAG, "time : " + time);

        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText(timeFormat(millisUntilFinished));
                time = millisUntilFinished;
                Log.d("time", String.valueOf(time));

                SharedPreferences sharedPreferences = getSharedPreferences("pref", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("SharedTime", time);
                editor.putLong("onPause", 0);
                editor.commit();

                if (time > 0) {
                    // timer notification 설정
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "default");
                    builder.setSmallIcon(R.drawable.timer);
                    builder.setContentTitle("Timer");
                    builder.setContentText(timeFormat(time));
                    builder.setDefaults(Notification.DEFAULT_ALL);
                    builder.addAction(R.drawable.ic_home_black_24dp, "Cancel", cancelIntent);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel("default", "basic channel", NotificationManager.IMPORTANCE_LOW);
                        channel.setVibrationPattern(new long[]{0}); // 진동 무음
                        channel.enableVibration(true); // 진동
                        channel.setSound(null, null);
                        notificationManager.createNotificationChannel(channel);


                    }

                    notificationManager.notify(1, builder.build());
                }

            }

            @Override
            public void onFinish() {

                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                // 종료
                SharedPreferences sp = getSharedPreferences("pref", 0);
                Long sharedTime = sp.getLong("SharedTime", 0);

                if (sharedTime < 1000) {

                    // 진동
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                    if (Build.VERSION.SDK_INT >= 26) {
                        vibrator.vibrate(VibrationEffect.createOneShot(4000, 10));
                    } else {
                        vibrator.vibrate(4000);
                    }

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "default");
                    builder.setSmallIcon(R.drawable.timer);
                    builder.setContentTitle("Timer");
                    builder.setContentText("Finish");
                    builder.setContentIntent(mPendingIntent);
                    builder.setDefaults(Notification.DEFAULT_ALL);
                    NotificationManager notificationManager2 = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel("default", "basic channel", NotificationManager.IMPORTANCE_DEFAULT);
                        channel.setVibrationPattern(new long[]{0}); // 진동 무음
                        channel.enableVibration(true); // 진동 무음
                        notificationManager2.createNotificationChannel(channel);

                    }

                    soundSetting(mediaPlayer);



                }


                stop_btn.setVisibility(View.INVISIBLE);
                cancel_btn.setVisibility(View.INVISIBLE);
                restart_btn.setVisibility(View.INVISIBLE);

                time = 0;

                textView.setText("00:00:00");

                SharedPreferences sharedPreferences = getSharedPreferences("pref", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("SharedTime", time);
                editor.putLong("onPause", 1);
                editor.commit();


            }
        }.start();
        OnStopBtnClick ostbc = new OnStopBtnClick();
        stop_btn.setOnClickListener(ostbc);

        OnRestartBtnClick orbc = new OnRestartBtnClick();
        restart_btn.setOnClickListener(orbc);

        OnCancelBtnClick ocbc = new OnCancelBtnClick();
        cancel_btn.setOnClickListener(ocbc);

    }

    public String timeFormat(long time) {
        int h = (int) (time / 3600000);
        int m = (int) (time - h * 3600000) / 60000;
        int s = (int) (time - h * 3600000 - m * 60000) / 1000;
        String t = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);

        return t;
    }

    class OnCancelBtnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            SharedPreferences sharedPreferences = getSharedPreferences("pref", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("SharedTime", 0);
            editor.putLong("onPause", 1);
            editor.commit();

            countDownTimer.cancel();
            time = 0;

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(1);

            finish();

        }
    }

    class OnStopBtnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            stop_btn.setVisibility(View.INVISIBLE);
            cancel_btn.setVisibility(View.VISIBLE);
            restart_btn.setVisibility(View.VISIBLE);

            countDownTimer.cancel();

            SharedPreferences sharedPreferences = getSharedPreferences("pref", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("onPause", 1);
            editor.commit();
        }


    }

    class OnRestartBtnClick implements View.OnClickListener {

        @Override
        public void onClick(final View v) {
            stop_btn.setVisibility(View.VISIBLE);
            cancel_btn.setVisibility(View.VISIBLE);
            restart_btn.setVisibility(View.INVISIBLE);

            SharedPreferences sharedPreferences = getSharedPreferences("pref", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("onPause", 0);
            editor.commit();


            countDownTimer = new CountDownTimer(time, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    textView.setText(timeFormat(millisUntilFinished));
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    time = millisUntilFinished;
                    Log.d("time", String.valueOf(time));

                    mediaPlayer = null;

                    SharedPreferences sharedPreferences = getSharedPreferences("pref", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("SharedTime", time);
                    editor.putLong("onPause", 0);
                    editor.commit();

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "default");
                    builder.setSmallIcon(R.drawable.timer);
                    builder.setContentTitle("Timer");
                    builder.setContentText(timeFormat(time));
                    builder.setDefaults(Notification.DEFAULT_ALL);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel("default", "basic channel", NotificationManager.IMPORTANCE_LOW);
                        channel.setVibrationPattern(new long[]{0}); // 진동 무음
                        channel.enableVibration(true); // 진동 무음
                        channel.setSound(null, null);
                        notificationManager.createNotificationChannel(channel);

                    }

                    notificationManager.notify(1, builder.build());

                }

                @Override
                public void onFinish() {

                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                    SharedPreferences sp = getSharedPreferences("pref", 0);
                    Long sharedTime = sp.getLong("SharedTime", 0);

                    if (sharedTime == 0) {

                        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                        if (Build.VERSION.SDK_INT >= 26) {
                            vibrator.vibrate(VibrationEffect.createOneShot(4000, 10));
                        } else {
                            vibrator.vibrate(4000);
                        }

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "default");
                        builder.setSmallIcon(R.drawable.timer);
                        builder.setContentTitle("Timer");
                        builder.setContentText("Finish");
                        builder.setContentIntent(mPendingIntent);
                        builder.setDefaults(Notification.DEFAULT_ALL);
                        NotificationManager notificationManager2 = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotificationChannel channel = new NotificationChannel("default", "basic channel", NotificationManager.IMPORTANCE_DEFAULT);
                            channel.setVibrationPattern(new long[]{0}); // 진동 무음
                            channel.enableVibration(true); // 진동 무음
                            notificationManager2.createNotificationChannel(channel);

                        }


                        soundSetting(mediaPlayer);


                    }


                    stop_btn.setVisibility(View.INVISIBLE);
                    cancel_btn.setVisibility(View.INVISIBLE);
                    restart_btn.setVisibility(View.INVISIBLE);

                    time = 0;

                    textView.setText("00:00:00");

                    SharedPreferences sharedPreferences = getSharedPreferences("pref", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("SharedTime", time);
                    editor.putLong("onPause", 1);
                    editor.commit();


                }
            }.start();

        }
    }

    @Override
    public void onBackPressed() {

        SharedPreferences sp = getSharedPreferences("pref", 0);
        Long onPause = sp.getLong("onPause", 1);
        Long sharedTime = sp.getLong("SharedTime", 0);

        if (sharedTime > 0 && onPause == 0) {
            Intent intent = new Intent(Intent.ACTION_MAIN); //태스크의 첫 액티비티로 시작
            intent.addCategory(Intent.CATEGORY_HOME);   //홈화면 표시
            startActivity(intent);
        }

        // 리소스 해제
        if (onPause == 1) {
            SharedPreferences sharedPreferences = getSharedPreferences("pref", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("SharedTime", 0);
            editor.putLong("onPause", 1);
            editor.commit();
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(1);
            countDownTimer = null;
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
            finish();
        }


    }

    private void soundSetting(MediaPlayer mediaPlayer) {

        // 사운드 설정
        SharedPreferences sp = getSharedPreferences("pref", 0);
        int soundnum = sp.getInt("soundnum", 1);

        if (soundnum == 1) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);
            ringtone.play();

        } else if (soundnum == 2) {

            startMedia(R.raw.trumpet);

        } else if (soundnum == 3) {

            startMedia(R.raw.clucking);

        } else if (soundnum == 4) {

            startMedia(R.raw.beep);

        } else if (soundnum == 5) {

            startMedia(R.raw.school);

        } else if (soundnum == 6) {

            startMedia(R.raw.airhorn);

        } else if (soundnum == 7) {

            startMedia(R.raw.boathorn);

        } else if (soundnum == 8) {
            startMedia(R.raw.rubberduck);

        } else if (soundnum == 9) {
            startMedia(R.raw.jinglebell);

        } else if (soundnum == 10) {
            startMedia(R.raw.musicbox);
        }
    }

    private void startMedia(int id) {

        mediaPlayer = new MediaPlayer();

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer = MediaPlayer.create(getApplicationContext(), id);
        mediaPlayer.start();
    }

}
