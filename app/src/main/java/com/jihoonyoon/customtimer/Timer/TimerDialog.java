package com.jihoonyoon.customtimer.Timer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.jihoonyoon.customtimer.R;


public class TimerDialog extends DialogFragment {


    private Context context;

    TextView dialog_hour_text, dialog_min_text, dialog_sec_text;
    EditText dialog_hour_edit, dialog_min_edit, dialog_sec_edit;
    Button dialog_cancel_btn, dialog_ok_btn;
    int h, m, s;
    Long time;
    String time_Text;
    private DataInputListener listener;

    // passing data
    public static TimerDialog newInstance(DataInputListener listener) {
        TimerDialog fragment = new TimerDialog();
        fragment.listener = listener;
        return fragment;
    }

    // passing data to timerfragment by interface
    public interface DataInputListener
    {
        void onDataInputComplete(String t, Long timer_time);
    }

    // setting dialog size
    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = params.MATCH_PARENT;
        params.height = params.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timer_dialog, container, false);

        SharedPreferences sp = this.getActivity().getSharedPreferences("pref", 0);
        int language = sp.getInt("Language", 0);

        dialog_hour_text = (TextView) view.findViewById(R.id.dialog_hour_text);
        dialog_min_text = (TextView) view.findViewById(R.id.dialog_min_text);
        dialog_sec_text = (TextView) view.findViewById(R.id.dialog_sec_text);

        dialog_hour_edit = (EditText) view.findViewById(R.id.dialog_hour_edit);
        dialog_min_edit = (EditText) view.findViewById(R.id.dialog_min_edit);
        dialog_sec_edit = (EditText) view.findViewById(R.id.dialog_sec_edit);

        dialog_cancel_btn = (Button) view.findViewById(R.id.dialog_cancel_btn);
        dialog_ok_btn = (Button) view.findViewById(R.id.dialog_ok_btn);

        if(language == 0){
            dialog_hour_text.setText("시");
            dialog_min_text.setText("분");
            dialog_sec_text.setText("초");
            dialog_cancel_btn.setText("취소");
            dialog_ok_btn.setText("확인");
        }else if(language == 1){
            dialog_hour_text.setText("Hour");
            dialog_min_text.setText("Min");
            dialog_sec_text.setText("Sec");
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

                // passing data
                listener.onDataInputComplete(time_Text,time);

                dismiss();
            }
        });
        return view;
    }
}
