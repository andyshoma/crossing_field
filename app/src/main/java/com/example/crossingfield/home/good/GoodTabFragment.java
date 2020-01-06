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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.crossingfield.R;
import com.example.crossingfield.home.search.GoodImageTask;
import com.example.crossingfield.home.search.SearchTabFragment;
import com.example.crossingfield.lib.User;
import com.example.crossingfield.lib.MySocket;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class GoodTabFragment extends Fragment {

    private ArrayList<User> users;
    private CountDownLatch latch;
    private CountDownLatch latch2;
    private ArrayList<String> jsons;
    private ArrayList<Bitmap> images;

    public class Person{
        @SerializedName("gender")
        @Expose
        public String gender;
        @SerializedName("age")
        @Expose
        public Integer age;
        @SerializedName("pass")
        @Expose
        public String password;
        @SerializedName("area")
        @Expose
        public String area;
        @SerializedName("name")
        @Expose
        public String user_name;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_tab_good, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);

        // getGoodUser();
        latch = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                GoodReceiveTask task = new GoodReceiveTask(getContext(), latch);
                task.setOnCallBack(new GoodReceiveTask.GoodReceiveCallBackTask(){
                    @Override
                    public void CallBack(String result) {
                        jsons = new ArrayList<>(Arrays.asList(result.split("@")));
                        latch2 = new CountDownLatch(jsons.size());
                    }
                });

                task.execute();

                try{
                    latch.await();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                for (int i=0; i<jsons.size(); i++){
                    String json = jsons.get(i);
                    Gson gson = new Gson();
                    Person person = gson.fromJson(json, Person.class);
                    User user = setUser(person);
                    users.add(user);
                    GoodImageTask giTask = new GoodImageTask(getContext(), latch2);
                    giTask.setName(user.getUsername());
                    giTask.setOnCallBack(new GoodImageTask.GoodImageCallBackTask(){
                        @Override
                        public void CallBack(Bitmap result){
                            images.add(result);
                        }
                    });
                }

                try {
                    latch2.await();
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                for(int i = 0; i < users.size(); i++){
                    users.get(i).setPhoto(images.get(i));
                }
            }
        }).start();

        sampleUsers();

        ListView listView = getActivity().findViewById(R.id.good_list);

        BaseAdapter adapter = new GoodListAdapter(getActivity().getApplicationContext(), users);

        listView.setAdapter(adapter);

    }

    private void setGoodUsers(ArrayList<User> users){
        this.users = users;
    }

    private User setUser(Person person){
        User user = new User();
        user.setGender(person.gender);
        user.setOld(person.age);
        user.setArea(person.area);
        user.setUsername(person.user_name);

        return user;
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