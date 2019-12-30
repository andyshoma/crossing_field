package com.example.crossingfield.lib;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.crossingfield.home.good.GoodTabFragment;
import com.example.crossingfield.home.meeting.CrossingTabFragment;
import com.example.crossingfield.home.meeting.MeetingTabFragment;
import com.example.crossingfield.home.message.MessageTabFragment;
import com.example.crossingfield.home.mypage.MypageTabFragment;
import com.example.crossingfield.home.search.SearchContainerFragment;

public class MeetCrossFragmentPagerAdapter extends FragmentPagerAdapter {

    private CharSequence[] tabTitles = {"すれ違い", "待ち合わせ"};

    public MeetCrossFragmentPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                return new MeetingTabFragment();
            case 1:
                return new CrossingTabFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
