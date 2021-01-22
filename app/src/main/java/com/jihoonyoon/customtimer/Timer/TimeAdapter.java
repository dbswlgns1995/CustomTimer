package com.jihoonyoon.customtimer.Timer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jihoonyoon.customtimer.MainActivity;
import com.jihoonyoon.customtimer.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class TimeAdapter extends RealmRecyclerViewAdapter<Time, TimeAdapter.ItemViewHolder> {

    MainActivity mainActivity = new MainActivity();
    private Realm realm;

    Context context;
    private BottomNavigationView mBtmView;


    public TimeAdapter(RealmResults<Time> realmResults, Context context) {
        super(realmResults, true);
        this.context = context;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if (viewType == R.layout.sample) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.additem, parent, false);
        }

        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        SharedPreferences sp = context.getSharedPreferences("pref", 0);
        int language = sp.getInt("Language", 0);
        int theme = sp.getInt("Theme", 1);

        if (position == realm.where(Time.class).findAll().size()) {
            holder.add_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                    MyTimerDialog myTimerDialog = MyTimerDialog.newInstance();
                    myTimerDialog.show(fm, "Tag");

                }
            });

            // add 버튼 테마설정
            if(theme == 1){
                holder.add_btn.setBackgroundResource(android.R.drawable.ic_menu_add);

            }else if(theme == 2){
                holder.add_btn.setBackgroundResource(android.R.drawable.ic_menu_add);

            }else if(theme == 3){
                holder.add_btn.setBackgroundResource(R.drawable.polaroid_btn);

            }else if(theme == 4){
                holder.add_btn.setBackgroundResource(R.drawable.note_btn);

            }else if(theme == 5){
                holder.add_btn.setBackgroundResource(android.R.drawable.ic_menu_add);

            }else if(theme == 6){
                holder.add_btn.setBackgroundResource(R.drawable.pumpkin_plus_btn);

            }else if(theme == 7){
                holder.add_btn.setBackgroundResource(R.drawable.ocean_btn);

            }else if(theme == 8){
                holder.add_btn.setBackgroundResource(R.drawable.egg_plus_btn);

            }else if(theme == 9){
                holder.add_btn.setBackgroundResource(R.drawable.mole_plus_btn);
            }

        } else {
            final Time obj = getItem(position);
            holder.data = obj;

            final int time = obj.getTime();
            final String name = obj.getName();

            int h = time / 3600;
            int m = time % 3600 / 60;
            int s = time % 3600 % 60;

            final String time_setting = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);

            holder.time_text.setText(time_setting);
            holder.name_text.setText(name);
            holder.sample_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(v.getContext(), TimerActivity.class);
                    intent1.putExtra("MyTimerTime", Long.valueOf(time) * 1000);
                    v.getContext().startActivity(intent1);

                }
            });

            // sample 버튼 테마설정
            if(theme == 1){
                holder.time_text.setTextColor(Color.BLACK);
                holder.name_text.setTextColor(Color.BLACK);
                holder.sample_layout.setBackgroundResource(R.drawable.roundedge);

            }else if(theme == 2){
                holder.time_text.setTextColor(Color.WHITE);
                holder.name_text.setTextColor(Color.WHITE);
                holder.sample_layout.setBackgroundResource(R.drawable.roundedge_white);

            }else if(theme == 3){
                holder.time_text.setTextColor(Color.WHITE);
                holder.name_text.setTextColor(Color.WHITE);
                holder.sample_layout.setBackgroundResource(R.drawable.polaroid_btn);


            }else if(theme == 4){
                holder.time_text.setTextColor(Color.BLACK);
                holder.name_text.setTextColor(Color.BLACK);
                holder.sample_layout.setBackgroundResource(R.drawable.note_btn);

            }else if(theme == 5){
                holder.time_text.setTextColor(Color.WHITE);
                holder.name_text.setTextColor(Color.WHITE);
                holder.sample_layout.setBackground(null);

            }else if(theme == 6){
                holder.time_text.setTextColor(Color.BLACK);
                holder.name_text.setTextColor(Color.BLACK);
                holder.sample_layout.setBackgroundResource(R.drawable.pumpkin_btn);

            }else if(theme == 7){
                holder.time_text.setTextColor(Color.BLACK);
                holder.name_text.setTextColor(Color.BLACK);
                holder.sample_layout.setBackgroundResource(R.drawable.ocean_btn);

            }else if(theme == 8){
                holder.time_text.setTextColor(Color.BLACK);
                holder.name_text.setTextColor(Color.BLACK);
                holder.sample_layout.setBackgroundResource(R.drawable.egg_btn);

            }else if(theme == 9){
                holder.time_text.setTextColor(Color.WHITE);
                holder.name_text.setTextColor(Color.WHITE);
                holder.sample_layout.setBackgroundResource(R.drawable.mole_btn);
            }

            // long 클릭시 sample 타이머 삭제
            if (language == 0) {
                holder.sample_layout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("삭제하시겠습니까?");
                        builder.setPositiveButton("예",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        realm = Realm.getDefaultInstance();
                                        realm.executeTransactionAsync(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                RealmResults<Time> results = realm.where(Time.class).equalTo("name", name).equalTo("time", time).findAll();
                                                results.deleteAllFromRealm();
                                            }
                                        });
                                    }
                                });
                        builder.setNegativeButton("아니오",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        builder.show();
                        return true;
                    }
                });

            } else if (language == 1) {

                holder.sample_layout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Delete?");
                        builder.setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        realm = Realm.getDefaultInstance();
                                        realm.executeTransactionAsync(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                RealmResults<Time> results = realm.where(Time.class).equalTo("name", name).equalTo("time", time).findAll();
                                                results.deleteAllFromRealm();
                                            }
                                        });
                                    }
                                });
                        builder.setNegativeButton("NO",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        builder.show();
                        return true;
                    }
                });

            }

        }
    }


    @Override
    public int getItemViewType(int position) {
        return (position == realm.where(Time.class).findAll().size()) ? R.layout.additem : R.layout.sample;
    }

    @Override
    public int getItemCount() {
        realm = Realm.getDefaultInstance();

        if (realm.where(Time.class).findAll() == null) {
            return 1;
        } else {
            return realm.where(Time.class).findAll().size() + 1;
        }

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView time_text, name_text;
        Button add_btn;
        LinearLayout sample_layout;
        public Time data;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            time_text = itemView.findViewById(R.id.sample_time_text);
            name_text = itemView.findViewById(R.id.sample_name_text);
            add_btn = itemView.findViewById(R.id.additem_btn);
            sample_layout = itemView.findViewById(R.id.sample_layout);




        }
    }
}
