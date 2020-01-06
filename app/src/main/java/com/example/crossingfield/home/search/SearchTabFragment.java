package com.example.crossingfield.home.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;

public class SearchTabFragment extends Fragment {

    private ArrayList<User> users;
    private View view;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        try {
            users = (ArrayList<User>) getArguments().getSerializable("users");
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_tab_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);

        users = new ArrayList<>();

        ListView listView = view.findViewById(R.id.search_list);

        BaseAdapter adapter = new SearchListAdapter(getContext(), users);
        listView.setAdapter(adapter);

        ImageButton searchButton = (ImageButton) view.findViewById(R.id.search);
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                if (fragmentManager != null){
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, SearchFragment.newInstance());
                    fragmentTransaction.commit();
                }
            }
        });
    }

}