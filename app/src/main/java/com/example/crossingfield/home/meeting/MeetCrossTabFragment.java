package com.example.crossingfield.home.meeting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.crossingfield.R;
import com.example.crossingfield.lib.HomeFragmentPagerAdapter;
import com.example.crossingfield.lib.MeetCrossFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MeetCrossTabFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_tab_meet_cross, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);

        MeetCrossFragmentPagerAdapter adapter = new MeetCrossFragmentPagerAdapter(getFragmentManager());
        final ViewPager viewPager = (ViewPager)view.findViewById(R.id.pager_meet_cross);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.tabs_meet_cross);
        tabLayout.setupWithViewPager(viewPager);

    }
}
