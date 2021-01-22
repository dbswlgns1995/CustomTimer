package com.jihoonyoon.customtimer.ui.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.jihoonyoon.customtimer.R;
import com.jihoonyoon.customtimer.Timer.TimerActivity;
import com.jihoonyoon.customtimer.Timer.TimerDialog;

public class DashboardFragment extends Fragment {

    // 시간을 세팅하여 timerActivity로 data pass

    TextView textView;
    Button start_btn;
    Button ten_Sec_btn, one_Min_btn, one_Hour_btn, thirty_Sec_btn, reset_btn;
    long time;
    String TAG = "Logd";



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        SharedPreferences sp = this.getActivity().getSharedPreferences("pref", 0);
        Long sharedTime = sp.getLong("SharedTime", 0);
        Long onPause = sp.getLong("onPause", 1);
        int language = sp.getInt("Language", 0);

        textView = (TextView) root.findViewById(R.id.timer_text);
        start_btn = (Button) root.findViewById(R.id.start_btn);

        ten_Sec_btn = (Button) root.findViewById(R.id.ten_sec_btn);
        one_Min_btn = (Button) root.findViewById(R.id.one_min_btn);
        one_Hour_btn = (Button) root.findViewById(R.id.one_hour_btn);
        thirty_Sec_btn = (Button) root.findViewById(R.id.thirty_sec_btn);
        reset_btn = (Button) root.findViewById(R.id.reset_btn);

        if(language == 0){
            start_btn.setText("시작");
            reset_btn.setText("초기화");
            ten_Sec_btn.setText("+10초");
            thirty_Sec_btn.setText("+30초");
            one_Min_btn.setText("+1분");
            one_Hour_btn.setText("+1시간");
        }else if(language == 1){
            start_btn.setText("START");
            reset_btn.setText("RESET");
            ten_Sec_btn.setText("+10SEC");
            thirty_Sec_btn.setText("+30SEC");
            one_Min_btn.setText("+1MIN");
            one_Hour_btn.setText("+1HOUR");
        }


        OnTextClick otc = new OnTextClick();
        textView.setOnClickListener(otc);

        OnStartBtnClick osbc = new OnStartBtnClick();
        start_btn.setOnClickListener(osbc);

        OntenSecBtnClick otsbc = new OntenSecBtnClick();
        ten_Sec_btn.setOnClickListener(otsbc);

        OnthirtySecBtnClick ottsbc = new OnthirtySecBtnClick();
        thirty_Sec_btn.setOnClickListener(ottsbc);


        OnOneMinBtnClick oombc = new OnOneMinBtnClick();
        one_Min_btn.setOnClickListener(oombc);

        OnOneHourBtnClick oohbc = new OnOneHourBtnClick();
        one_Hour_btn.setOnClickListener(oohbc);

        OnResetBtnClick orsbc = new OnResetBtnClick();
        reset_btn.setOnClickListener(orsbc);


        return root;
    }

    public String timeFormat(long time) {
        int h = (int) (time / 3600000);
        int m = (int) (time - h * 3600000) / 60000;
        int s = (int) (time - h * 3600000 - m * 60000) / 1000;
        String t = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);

        return t;
    }


    // text click to dialog
    class OnTextClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //TimerDialog timerDialog = new TimerDialog(getContext());
            //timerDialog.callFunction(textView);

            TimerDialog dialog = TimerDialog.newInstance(new TimerDialog.DataInputListener() {
                @Override
                public void onDataInputComplete(String t, Long timer_time) {
                    textView.setText(t);
                    time = timer_time;
                }
            });

            dialog.show(getFragmentManager(), "addDialog");

            Log.d("time", String.valueOf(time));


        }
    }

    class OnStartBtnClick implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            if(time>0){
                Intent intent = new Intent(getActivity(), TimerActivity.class);
                intent.putExtra("TimerTime", time);
                startActivity(intent);
            }

        }

    }


    class OntenSecBtnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            time = time + 10000;
            textView.setText(timeFormat(time));
            Log.d("time", String.valueOf(time));
            start_btn.setVisibility(View.VISIBLE);
        }
    }

    class OnthirtySecBtnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            time = time + 30000;
            textView.setText(timeFormat(time));
            Log.d("time", String.valueOf(time));
            start_btn.setVisibility(View.VISIBLE);
        }
    }

    class OnOneMinBtnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            time = time + (1000 * 60);
            textView.setText(timeFormat(time));
            Log.d("time", String.valueOf(time));
            start_btn.setVisibility(View.VISIBLE);
        }
    }

    class OnOneHourBtnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            time = time + (1000 * 3600);
            textView.setText(timeFormat(time));
            Log.d("time", String.valueOf(time));
            start_btn.setVisibility(View.VISIBLE);
        }
    }

    class OnResetBtnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            time = 0;
            textView.setText(timeFormat(time));
            Log.d("time", String.valueOf(time));
            start_btn.setVisibility(View.VISIBLE);
            SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("pref", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("SharedTime", time);
            editor.putLong("onPause", 1);
            editor.commit();

        }
    }
}


