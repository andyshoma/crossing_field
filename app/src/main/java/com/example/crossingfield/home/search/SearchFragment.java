package com.example.crossingfield.home.search;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.crossingfield.R;
import com.example.crossingfield.home.HomeActivity;
import com.example.crossingfield.home.message.MessageListAdapter;
import com.example.crossingfield.lib.MyHTTP;
import com.example.crossingfield.lib.MySocket;
import com.example.crossingfield.lib.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.example.crossingfield.lib.MathConstants.*;
import static com.example.crossingfield.lib.StringConstants.*;

public class SearchFragment extends Fragment {

    private View view1;

    private RadioGroup rg;
    private EditText oldFrom;
    private EditText oldTo;
    private Spinner areaSpinner;

    private String area;
    private String sendMessage;
    private ArrayList<String> jsons;
    public ArrayList<User> users;
    private User my_user;

    public SearchTask sTask;
    public CountDownLatch latch;
    public CountDownLatch latch2;

    public FragmentManager fragmentManager;

    public static SearchFragment newInstance(){
        SearchFragment searchFragment = new SearchFragment();

        return searchFragment;
    }

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

        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new InputStreamReader(getContext().openFileInput("init.txt")));
            String str = null;
            str = reader.readLine();
            my_user = new Gson().fromJson(str, User.class);
            System.out.println(my_user.toString());
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, final Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);

        this.view1 = view;

        rg = (RadioGroup) view.findViewById(R.id.search_radio);

        oldFrom = (EditText)view.findViewById(R.id.old_edit1);
        oldTo = (EditText)view.findViewById(R.id.old_edit2);

        areaSpinner = (Spinner)view.findViewById(R.id.search_area);
        ArrayAdapter areaAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.area, android.R.layout.simple_spinner_item);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(areaAdapter);

        fragmentManager = getFragmentManager();

        users = new ArrayList<>();

        sTask = new SearchTask(getContext());

        // 都道府県のSpinner
        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner spinner = (Spinner)adapterView;
                area = (String) spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        final Button searchButton = (Button) view.findViewById(R.id.search_done);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                latch = new CountDownLatch(1);

                int checkedId = rg.getCheckedRadioButtonId();

                String gender = null;
                if (checkedId != -1) {
                    // 選択されているラジオボタンの取得
                    RadioButton genderButton = (RadioButton) view1.findViewById(checkedId);
                    gender = genderButton.getText().toString();
                    if (gender.equals("男性") == true) {
                        gender = "male";
                    } else {
                        gender = "famale";
                    }
                }

                String old = oldFrom.getText().toString() + ',' + oldTo.getText().toString();

                sendMessage = String.valueOf(SEARCH) + ',' + gender + ',' + old + ',' + area + ',';
                sTask.setPram(latch, sendMessage);
                sTask.setOnCallBack(new SearchTask.SearchCallBackTask() {
                    @Override
                    public void CallBack(String result) {
                        super.CallBack(result);
                        ArrayList<String> js = new ArrayList<>(Arrays.asList(result.split("@")));
                        jsons = js;
                        latch2 = new CountDownLatch(jsons.size());
                        System.out.println(jsons);
                    }
                });

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sTask.execute();

                        try{
                            latch.await();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }

                        imageGetRequest();

                        try {
                            latch2.await();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }

                        for(int i = 0; i < users.size(); i++){
                            if (users.get(i).getPhoto() != null){
                                System.out.println("photo");
                            }
                        }

                        System.out.println("set fin");

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                        Bundle bundle = new Bundle();
                        System.out.println("s:" + users.size());
                        bundle.putSerializable("users", users);
                        bundle.putSerializable("my_user", my_user);

                        SearchTabFragment fragment = new SearchTabFragment();
                        fragment.setArguments(bundle);

                        if (fragmentManager != null){
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container, fragment);
                            fragmentTransaction.commit();
                        }
                    }
                }).start();

            }
        });

        Button popButton = (Button)view.findViewById(R.id.pop01);
        popButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                SearchFragment searchFragment = new SearchFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("users", null);
                searchFragment.setArguments(bundle);

                if (fragmentManager != null){
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, new SearchTabFragment());
                    fragmentTransaction.commit();
                }
            }
        });
    }

    private void getImage(final String name, final Integer position){

        new AsyncTask<Void, Void, Bitmap>(){

            @Override
            protected Bitmap doInBackground(Void...voids) {

                System.out.println("latch:" + latch2.getCount());

                Bitmap image;
                String url = "http://192.168.2.200:8100/send";
                MyHTTP http = new MyHTTP(url);
                image = http.download(name);

                return image;
            }

            @Override
            protected void onPostExecute(Bitmap image) {
                super.onPostExecute(image);

                users.get(position).setPhoto(image);
                System.out.println("get");

                latch2.countDown();
            }
        }.execute();
    }

    public User setUser(Person person){
        User user = new User();
        user.setGender(person.gender);
        user.setOld(person.age);
        user.setArea(person.area);
        user.setUsername(person.user_name);

        return user;
    }

    public void imageGetRequest(){
        for (int i = 0; i < jsons.size(); i++){
            String json = jsons.get(i);
            Gson gson = new Gson();
            Person person = gson.fromJson(json, Person.class);
            System.out.println(person.password);
            User user = setUser(person);
            getImage(user.getUsername(), i);
            users.add(user);
        }
    }

}
