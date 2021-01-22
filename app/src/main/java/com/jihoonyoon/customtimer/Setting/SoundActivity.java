package com.jihoonyoon.customtimer.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jihoonyoon.customtimer.MainActivity;
import com.jihoonyoon.customtimer.R;

public class SoundActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton r1, r2, r3, r4, r5, r6, r7, r8, r9, r10;
    private Button select_btn;
    TextView title_text;
    int soundnum;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    AudioManager audioManager;
    int nMax;
    int nCurrentVolumn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        radioGroup = (RadioGroup) findViewById(R.id.sound_radioGroup);
        r1 = (RadioButton) findViewById(R.id.sound_s1_radioBtn);
        r2 = (RadioButton) findViewById(R.id.sound_s2_radioBtn);
        r3 = (RadioButton) findViewById(R.id.sound_s3_radioBtn);
        r4 = (RadioButton) findViewById(R.id.sound_s4_radioBtn);
        r5 = (RadioButton) findViewById(R.id.sound_s5_radioBtn);
        r6 = (RadioButton) findViewById(R.id.sound_s6_radioBtn);
        r7 = (RadioButton) findViewById(R.id.sound_s7_radioBtn);
        r8 = (RadioButton) findViewById(R.id.sound_s8_radioBtn);
        r9 = (RadioButton) findViewById(R.id.sound_s9_radioBtn);
        r10 = (RadioButton) findViewById(R.id.sound_s10_radioBtn);

        title_text = (TextView) findViewById(R.id.sound_title);

        select_btn = (Button) findViewById(R.id.sound_select_btn);

        seekBar = (SeekBar) findViewById(R.id.sound_seekBar);

        SharedPreferences sp = getSharedPreferences("pref", 0);
        int language = sp.getInt("Language", 0);

        // 언어 설정
        if(language == 0){
            title_text.setText("  타이머 사운드 설정");
            select_btn.setText("설정");
        }else if(language == 1){
            title_text.setText("  Timer sound setting");
            select_btn.setText("Select");
        }

        mediaPlayer = new MediaPlayer();
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        nMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        nCurrentVolumn = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        // SeekBar로 볼륨 조절
        seekBar.setMax(nMax);
        seekBar.setProgress(nCurrentVolumn);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // 라디오버튼 클릭시 해당 사운드 재생
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.sound_s1_radioBtn) {
                    soundnum = 1;

                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);
                    ringtone.play();


                } else if (checkedId == R.id.sound_s2_radioBtn) {
                    soundnum = 2;

                    startMedia(R.raw.trumpet);

                } else if (checkedId == R.id.sound_s3_radioBtn) {
                    soundnum = 3;

                    startMedia(R.raw.clucking);

                } else if (checkedId == R.id.sound_s4_radioBtn) {
                    soundnum = 4;

                    startMedia(R.raw.beep);

                } else if (checkedId == R.id.sound_s5_radioBtn) {
                    soundnum = 5;

                    startMedia(R.raw.school);

                } else if (checkedId == R.id.sound_s6_radioBtn) {
                    soundnum = 6;

                    startMedia(R.raw.airhorn);

                } else if (checkedId == R.id.sound_s7_radioBtn) {
                    soundnum = 7;

                    startMedia(R.raw.boathorn);

                } else if (checkedId == R.id.sound_s8_radioBtn) {
                    soundnum = 8;

                    startMedia(R.raw.rubberduck);

                } else if (checkedId == R.id.sound_s9_radioBtn) {
                    soundnum = 9;

                    startMedia(R.raw.jinglebell);

                } else if (checkedId == R.id.sound_s10_radioBtn) {
                    soundnum = 10;

                    startMedia(R.raw.musicbox);
                }
            }
        });

        // 사운드 선택하여 저장
        select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ddd", "num : " + soundnum);
                SharedPreferences sharedPreferences = getSharedPreferences("pref", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("soundnum", soundnum);
                editor.commit();
                finish();
            }
        });
    }

    private void startMedia(int id){
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer = MediaPlayer.create(getApplicationContext(), id);
        mediaPlayer.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // MediaPlayer 해지
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
