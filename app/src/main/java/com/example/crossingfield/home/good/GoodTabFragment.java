package com.example.crossingfield.home.good;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.crossingfield.R;
import com.example.crossingfield.lib.User;
import com.example.crossingfield.lib.MySocket;

import java.util.ArrayList;

public class GoodTabFragment extends Fragment {

    private ArrayList<User> users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_tab_good, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);

        // getGoodUser();

        sampleUsers();

        ListView listView = getActivity().findViewById(R.id.good_list);

        BaseAdapter adapter = new GoodListAdapter(getActivity().getApplicationContext(), users);

        listView.setAdapter(adapter);

    }

    private void setGoodUsers(ArrayList<User> users){
        this.users = users;
    }

    private void getGoodUser(){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                MySocket socket = new MySocket(getContext());
                String get_message = socket.getMessage();
                ArrayList<User> users = new ArrayList<>();

                /*
                    ここで受信したuser情報をarrayListに追加する
                 */

                setGoodUsers(users);

                return null;
            }
        }.execute();
    }

    private void sampleUsers(){
        ArrayList<User> users = new ArrayList<>();
        Resources r = getResources();
        Bitmap andy = BitmapFactory.decodeResource(r, R.drawable.monky);
        Bitmap maep = BitmapFactory.decodeResource(r, R.drawable.monky);
        Bitmap yossi = BitmapFactory.decodeResource(r, R.drawable.monky);
        Bitmap arthur = BitmapFactory.decodeResource(r, R.drawable.monky);

        users.add(new User("andy", "male", 21, "和歌山", andy));
        users.add(new User("maep", "male", 22, "香川", maep));
        users.add(new User("yossi", "male", 22, "岐阜", yossi));
        users.add(new User("arthur", "male", 22, "愛知", arthur));

        setGoodUsers(users);
    }
}