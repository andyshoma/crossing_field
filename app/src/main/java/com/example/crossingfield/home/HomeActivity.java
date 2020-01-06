package com.example.crossingfield.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crossingfield.R;
import com.example.crossingfield.lib.MyHTTP;
import com.example.crossingfield.lib.MySocket;
import com.google.android.material.tabs.TabLayout;

import static com.example.crossingfield.lib.MathConstants.*;

public class HomeActivity extends AppCompatActivity {

    public Context context;
    public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = getApplicationContext();
        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        try{
            Class.forName("android.os.AsyncTask");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        //init();

        HomeFragmentPagerAdapter adapter = new HomeFragmentPagerAdapter(getSupportFragmentManager());
        final ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(adapter);

        tabSetup(viewPager);
    }

    private void tabSetup(ViewPager viewPager){
        LayoutInflater inflater = LayoutInflater.from(context);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        View tabMeet = inflater.inflate(R.layout.layout_custom_tab, null);
        ((ImageView) tabMeet.findViewById(R.id.tab_icon)).setImageResource(R.drawable.meet);
        ((TextView) tabMeet.findViewById(R.id.tab_text)).setText("待ち合わせ");
        tabLayout.getTabAt(0).setCustomView(tabMeet);

        View tabSearch = inflater.inflate(R.layout.layout_custom_tab, null);
        ((ImageView) tabSearch.findViewById(R.id.tab_icon)).setImageResource(R.drawable.search);
        ((TextView) tabSearch.findViewById(R.id.tab_text)).setText("さがす");
        tabLayout.getTabAt(1).setCustomView(tabSearch);

        View tabMessage = inflater.inflate(R.layout.layout_custom_tab, null);
        ((ImageView) tabMessage.findViewById(R.id.tab_icon)).setImageResource(R.drawable.message);
        ((TextView) tabMessage.findViewById(R.id.tab_text)).setText("メッセージ");
        tabLayout.getTabAt(2).setCustomView(tabMessage);

        View tabGood = inflater.inflate(R.layout.layout_custom_tab, null);
        ((ImageView) tabGood.findViewById(R.id.tab_icon)).setImageResource(R.drawable.good);
        ((TextView) tabGood.findViewById(R.id.tab_text)).setText("相手から");
        tabLayout.getTabAt(3).setCustomView(tabGood);

        View tabMypage = inflater.inflate(R.layout.layout_custom_tab, null);
        ((ImageView) tabMypage.findViewById(R.id.tab_icon)).setImageResource(R.drawable.mypage);
        ((TextView) tabMypage.findViewById(R.id.tab_text)).setText("マイページ");
        tabLayout.getTabAt(4).setCustomView(tabMypage);
    }

    private void init(){
        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... voids){

                String send_message = String.valueOf(INIT) + ',' + name;

                MySocket socket = new MySocket(context);
                socket.setMessage(send_message);
                String get_message = socket.trySend();

                return get_message;
            }

            @Override
            protected void onPostExecute(String string){

            }
        }.execute();
    }
}