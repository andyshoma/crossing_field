package com.example.crossingfield.home.search;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.crossingfield.R;
import com.example.crossingfield.home.search.SearchFragment;
import com.example.crossingfield.lib.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SearchTabFragment extends Fragment {

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<String> names;
    private User my_user;
    private View view;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
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

        try {
            users = (ArrayList<User>) getArguments().getSerializable("users");
            System.out.println(users.size());
        }catch (NullPointerException e){
            users = new ArrayList<>();
        }

        return inflater.inflate(R.layout.fragment_tab_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);

        ImageButton searchButton = (ImageButton) view.findViewById(R.id.search);
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                if (fragmentManager != null){
                    System.out.println("hellow");
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, SearchFragment.newInstance());
                    fragmentTransaction.commit();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle saveInstaceState) {
        super.onActivityCreated(saveInstaceState);

        ListView listView = getActivity().findViewById(R.id.search_list);

        BaseAdapter adapter = new SearchListAdapter(getContext(), users);
        listView.setAdapter(adapter);
    }

    private void sampleUsers(){
        users = new ArrayList<>();
        Resources r = getResources();
        Bitmap andy = BitmapFactory.decodeResource(r, R.drawable.monky);
        Bitmap maep = BitmapFactory.decodeResource(r, R.drawable.monky);
        Bitmap yossi = BitmapFactory.decodeResource(r, R.drawable.monky);
        Bitmap arthur = BitmapFactory.decodeResource(r, R.drawable.monky);

        users.add(new User("andy", "male", 21, "和歌山", andy));
        users.add(new User("maep", "male", 22, "香川", maep));
        users.add(new User("yossi", "male", 22, "岐阜", yossi));
        users.add(new User("arthur", "male", 22, "愛知", arthur));
    }

}