package com.jihoonyoon.customtimer.Timer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jihoonyoon.customtimer.R;

import io.realm.Realm;

// 추가 버튼 누르면 다이얼로그로 mytimer 추가

public class MyTimerDialog extends DialogFragment {
    private Context context;

    TextView myname_text, hour_text, min_text, sec_text;
    EditText dialog_hour_edit, dialog_min_edit, dialog_sec_edit, name_edit;
    Button dialog_cancel_btn, dialog_ok_btn;
    int h, m, s;
    Long time;
    String time_Text, name_Text;

    public static MyTimerDialog newInstance() {
        return new MyTimerDialog();
    }

    public MyTimerDialog(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_timer_dialog, container, false);

        Realm.init(getContext());
        final Realm realm = Realm.getDefaultInstance();

        SharedPreferences sp = view.getContext().getSharedPreferences("pref", 0);
        int language = sp.getInt("Language", 0);

        myname_text = (TextView)view.findViewById(R.id.mydialog_TimerName_text);
        hour_text = (TextView)view.findViewById(R.id.mydialog_hour_text);
        min_text = (TextView)view.findViewById(R.id.mydialog_min_text);
        sec_text = (TextView)view.findViewById(R.id.mydialog_sec_text);

        name_edit = (EditText)view.findViewById(R.id.mydialog_name_edit);
        dialog_hour_edit = (EditText) view.findViewById(R.id.mydialog_hour_edit);
        dialog_min_edit = (EditText) view.findViewById(R.id.mydialog_min_edit);
        dialog_sec_edit = (EditText) view.findViewById(R.id.mydialog_sec_edit);

        dialog_cancel_btn = (Button) view.findViewById(R.id.mydialog_cancel_btn);
        dialog_ok_btn = (Button) view.findViewById(R.id.mydialog_ok_btn);


        //언어 설정
        if(language == 0){
            myname_text.setText("타이머 이름");
            name_edit.setHint("이름");
            hour_text.setText("시");
            min_text.setText("분");
            sec_text.setText("초");
            dialog_cancel_btn.setText("취소");
            dialog_ok_btn.setText("확인");
        }else if(language == 1){
            myname_text.setText("Timer name");
            name_edit.setHint("name");
            hour_text.setText("HOUR");
            min_text.setText("MIN");
            sec_text.setText("SEC");
            dialog_cancel_btn.setText("CANCEL");
            dialog_ok_btn.setText("OK");
        }


        // cancel btn -> just dismiss()
        dialog_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // ok btn -> setting time and data passing to timer_fragment
        dialog_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // when not specified time -> time set 0
                if (dialog_hour_edit.getText().toString().equals("")) {
                    h = 0;
                } else {
                    h = Integer.parseInt(dialog_hour_edit.getText().toString());
                }

                if (dialog_min_edit.getText().toString().equals("")) {
                    m = 0;
                } else {
                    m = Integer.parseInt(dialog_min_edit.getText().toString());
                }

                if (dialog_sec_edit.getText().toString().equals("")) {
                    s = 0;
                } else {
                    s = Integer.parseInt(dialog_sec_edit.getText().toString());
                }

                time = Long.valueOf(((h * 1000 * 60 * 60) + (m * 1000 * 60) + (s * 1000)));
                time_Text = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
                name_Text = name_edit.getText().toString();

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    Time myTime = new Time(Math.toIntExact(time/1000), name_Text);
                    realm.beginTransaction();
                    Time realmTime = realm.copyToRealm(myTime);
                    realm.commitTransaction();
                }

                dismiss();
            }
        });



        return view;
    }
}