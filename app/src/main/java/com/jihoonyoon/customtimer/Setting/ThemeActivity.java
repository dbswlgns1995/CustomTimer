package com.jihoonyoon.customtimer.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.jihoonyoon.customtimer.R;

public class ThemeActivity extends AppCompatActivity implements RewardedVideoAdListener{

    // 테마 선택

    private ImageView th1, th2, th3, th4, th5, th6, th7, th8, th9;
    private TextView network_text;
    private int num;

    private RewardedVideoAd mRewardedVideoAd;
    private int language;

    ConnectivityManager cm;
    NetworkInfo ni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);


        SharedPreferences sp = getSharedPreferences("pref", 0);
        language = sp.getInt("Language", 0);

        th1 = (ImageView) findViewById(R.id.theme_1_img);
        th2 = (ImageView) findViewById(R.id.theme_2_img);
        th3 = (ImageView) findViewById(R.id.theme_3_img);
        th4 = (ImageView) findViewById(R.id.theme_4_img);
        th5 = (ImageView) findViewById(R.id.theme_5_img);

        th6 = (ImageView) findViewById(R.id.theme_6_img);
        th7 = (ImageView) findViewById(R.id.theme_7_img);
        th8 = (ImageView) findViewById(R.id.theme_8_img);
        th9 = (ImageView) findViewById(R.id.theme_9_img);

        network_text = (TextView) findViewById(R.id.theme_network_textView);

        checkInternetState();

        // AD 로드
        //test
        MobileAds.initialize(this,  "ca-app-pub-3940256099942544~3347511713");
        //MobileAds.initialize(this, "ca-app-pub-9869278582663558~6301343680"); // real
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        th1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 1;
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
            }
        });

        th2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 2;
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
            }
        });

        th3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 3;
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
            }
        });

        th4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 4;
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
            }
        });

        th5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 5;
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
            }
        });

        th6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 6;
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
            }
        });

        th7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 7;
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
            }
        });

        th8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 8;
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
            }
        });

        th9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 9;
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
            }
        });
    }

    // 네트워크 연결 확인

    private void checkInternetState(){
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(!isConnected){
            network_text.setVisibility(View.VISIBLE);
            th1.setVisibility(View.INVISIBLE);
            th2.setVisibility(View.INVISIBLE);
            th3.setVisibility(View.INVISIBLE);
            th4.setVisibility(View.INVISIBLE);
            th5.setVisibility(View.INVISIBLE);
            th6.setVisibility(View.INVISIBLE);
            th7.setVisibility(View.INVISIBLE);
            th8.setVisibility(View.INVISIBLE);
            th9.setVisibility(View.INVISIBLE);
            if(language == 0){
                network_text.setText("네트워크 연결을 확인해 주세요");
            }else if(language == 1){
                network_text.setText("Please check your network connection");
            }
        }else{
            network_text.setVisibility(View.INVISIBLE);
            th1.setVisibility(View.VISIBLE);
            th2.setVisibility(View.VISIBLE);
            th3.setVisibility(View.VISIBLE);
            th4.setVisibility(View.VISIBLE);
            th5.setVisibility(View.VISIBLE);
            th6.setVisibility(View.VISIBLE);
            th7.setVisibility(View.VISIBLE);
            th8.setVisibility(View.VISIBLE);
            th9.setVisibility(View.VISIBLE);
        }

    }

    private void loadRewardedVideoAd() {
        //test
        //mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());

        mRewardedVideoAd.loadAd("ca-app-pub-9869278582663558/5474513027", new AdRequest.Builder().build());
    }

    public void saveSPtheme(int num) {
        SharedPreferences sharedPreferences = getSharedPreferences("pref", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Theme", num);
        editor.commit();
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        saveSPtheme(num);
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }


}
