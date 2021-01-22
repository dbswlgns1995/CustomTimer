package com.jihoonyoon.customtimer.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.jihoonyoon.customtimer.R;

public class QuestionActivity extends AppCompatActivity {

    TextView title_text, content_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        SharedPreferences sp = getSharedPreferences("pref", 0);
        int language = sp.getInt("Language", 0);

        title_text = (TextView) findViewById(R.id.question_title_text);
        content_text = (TextView) findViewById(R.id.question_content_text);

        if (language == 0) {
            title_text.setText("\n  사용방법");
            content_text.setText("\n\n 1. 내 타이머 화면의 '+' 버튼을 누르면 자신이 원하는 시간과 이름의 타이머를 만들 수 있습니다.\n\n" +
                    "  2. 내 타이머 화면에서 자신이 만든 타이머를 누르면 해당 시간에 맞춰 타이머가 시작됩니다.\n\n" +
                    "  3. 내 타이머 화면에서 자신이 만든 타이머를 꾹 눌러 타이머를 제거할 수 있습니다.\n\n" +
                    "  4. 내 타이머의 제목은 6글자까지 입력 가능합니다.\n\n" +
                    "  5. 타이머 화면에서 시간(00:00:00)을 누르면 빠르게 시간을 변경할 수 있습니다.\n\n" +
                    "  6. 설정 화면의 Language / 언어 메뉴에서 영어 혹은 한국어로 언어를 설정할 수 있습니다.\n\n" +
                    "  7. 설정 화면의 테마 메뉴에서 동영상 광고를 1회 시청하여 원하는 테마로 변경할 수 있습니다.\n\n" +
                    "  8. 설정 화면의 타이머 소리 설정 메뉴에서 원하는 타이머 소리를 설정할 수 있습니다.\n\n" +
                    "  9. 리뷰 작성으로 어플에 대한 피드백을 남겨주세요.");
        } else if (language == 1) {
            title_text.setText("\n  FAQ");
            content_text.setText("\n\n 1. Tap the '+' button on MyTimer screen to create a timer of your own time and name.\n\n" +
                    " 2. Tap the timer you created on MyTimer screen to start the corresponding timer.\n\n" +
                    " 3. You can remove timer by pressing the timer long that you created on the My Timer screen.\n\n" +
                    " 4. The title of my timer can be up to six characters long.\n\n" +
                    " 5. Press Time (00:00:00) on the timer screen to change time quickly.\n\n" +
                    " 6. You can change the language to English or Korean from the Language menu on the Setup screen.\n\n" +
                    " 7. You can change theme you want by watching AD once from the Theme menu on the Settings screen.\n\n" +
                    " 8. On the Setup screen, you can set the desired timer so und from the Timer Sound Settings menu.\n\n" +
                    " 9. Please leave your feedback on the application");

        }

    }
}