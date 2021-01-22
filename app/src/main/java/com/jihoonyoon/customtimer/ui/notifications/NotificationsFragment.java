package com.jihoonyoon.customtimer.ui.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.jihoonyoon.customtimer.MainActivity;
import com.jihoonyoon.customtimer.Setting.QuestionActivity;
import com.jihoonyoon.customtimer.R;
import com.jihoonyoon.customtimer.Setting.SoundActivity;
import com.jihoonyoon.customtimer.Setting.ThemeActivity;

public class NotificationsFragment extends Fragment {

    // 설정 fragment

    TextView question_textView, language_textView, sharedapp_textView, review_textView, theme_textView, sound_textView;
    TextView information_textView, design_textView;
    CharSequence[] languages = {"한국어", "English"};
    int nSelectItem;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        SharedPreferences sp = this.getActivity().getSharedPreferences("pref", 0);
        int language = sp.getInt("Language", 0);

        information_textView = (TextView) root.findViewById(R.id.setting_information);
        design_textView = (TextView) root.findViewById(R.id.setting_design);
        question_textView = (TextView) root.findViewById(R.id.setting_question);
        language_textView = (TextView) root.findViewById(R.id.setting_language);
        sharedapp_textView = (TextView) root.findViewById(R.id.setting_sharedapp);
        review_textView = (TextView) root.findViewById(R.id.setting_review);
        theme_textView = (TextView) root.findViewById(R.id.setting_theme);
        sound_textView = (TextView) root.findViewById(R.id.setting_sound);


        // 언어 설정
        if(language == 0){
            information_textView.setText("  정보");
            question_textView.setText("    사용 방법");
            sharedapp_textView.setText("    어플 공유하기");
            review_textView.setText("    리뷰 작성");
            design_textView.setText("  디자인");
            theme_textView.setText("    테마");
            sound_textView.setText("    타이머 소리 설정");
        }else if(language == 1){
            information_textView.setText("  Information");
            question_textView.setText("    FAQ");
            sharedapp_textView.setText("    Share");
            review_textView.setText("    Send feedback");
            design_textView.setText("  Design");
            theme_textView.setText("    Theme");
            sound_textView.setText("    Timer sound");
        }


        question_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), QuestionActivity.class);
                startActivity(intent);
            }
        });



        // language setting if 0 korean , 1 English
        language_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder oDialog = new AlertDialog.Builder(getContext());
                oDialog.setTitle("Language / 언어").setSingleChoiceItems(languages, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nSelectItem = which;
                    }
                }).setPositiveButton("선택", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(nSelectItem == 0){
                            SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("pref", 0);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("Language", 0);
                            editor.commit();
                            restartApp(getContext());
                        }else if(nSelectItem == 1){
                            SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("pref", 0);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("Language", 1);
                            editor.commit();
                            restartApp(getContext());

                        }

                    }
                }).show();
            }
        });

        // sound activity로 이동
        sound_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SoundActivity.class);
                startActivity(intent);
            }
        });

        // theme activity로 이동
        theme_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ThemeActivity.class);
                startActivity(intent);
            }
        });

        // 어플 공유
        sharedapp_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent msg = new Intent(Intent.ACTION_SEND);
                msg.addCategory(Intent.CATEGORY_DEFAULT);
                msg.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.jihoonyoon.customtimer");
                msg.putExtra(Intent.EXTRA_TITLE, "제목");
                msg.setType("text/plain");
                startActivity(Intent.createChooser(msg, "Shared"));
            }
        });

        // 어플 상세창으로 이동
        review_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.jihoonyoon.customtimer"));
                startActivity(intent);

            }
        });

        return root;
    }


    // 어플 강제 재실행
    public static void restartApp(Context context) {
        Intent mStartActivity = new Intent(context, MainActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }
}