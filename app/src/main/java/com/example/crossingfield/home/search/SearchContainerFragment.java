package com.example.crossingfield.home.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.crossingfield.R;

public class SearchContainerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        System.out.println("hey");
        return inflater.inflate(R.layout.fragment_container_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        if (fragmentManager != null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new SearchTabFragment());
            fragmentTransaction.commit();
        }
    }
}
