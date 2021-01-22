package com.jihoonyoon.customtimer.ui.home;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jihoonyoon.customtimer.R;
import com.jihoonyoon.customtimer.Timer.Time;
import com.jihoonyoon.customtimer.Timer.TimeAdapter;

import io.realm.Realm;
import io.realm.RealmResults;

public class HomeFragment extends Fragment {

    private Realm realm;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences sp = root.getContext().getSharedPreferences("pref", 0);
        int theme = sp.getInt("Theme", 1);


        Realm.init(getContext());
        realm = Realm.getDefaultInstance();

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);

        // 배경화면 테마 설정

        if(theme == 1){
            recyclerView.setBackgroundColor(Color.WHITE);
        }else if(theme == 2){
            recyclerView.setBackgroundColor(Color.BLACK);
        }else if(theme == 3){
            recyclerView.setBackgroundResource(R.drawable.polariod_bg);
        }else if(theme == 4){
            recyclerView.setBackgroundResource(R.drawable.note_bg);
        }else if(theme == 5){
            recyclerView.setBackgroundResource(R.drawable.blackboard_bg);
        }else if(theme == 6){
            recyclerView.setBackgroundResource(R.drawable.pumpkin_bg);
        }else if(theme == 7){
            recyclerView.setBackgroundResource(R.drawable.ocean_bg);
        }else if(theme == 8){
            recyclerView.setBackgroundResource(R.drawable.egg_bg);
        }else if(theme == 9){
            recyclerView.setBackgroundResource(R.drawable.mole_bg);
        }

        // realm recyclerview adapter 연결
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        RealmResults<Time> myTime = realm.where(Time.class).findAll();
        recyclerView.setLayoutManager(gridLayoutManager);
        final TimeAdapter adapter = new TimeAdapter(myTime, getContext());
        recyclerView.setAdapter(adapter);

        return root;


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recyclerView.setAdapter(null);
        realm.close();
    }
}
