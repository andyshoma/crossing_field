package com.example.crossingfield.home;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.crossingfield.home.meeting.MeetCrossTabFragment;
import com.example.crossingfield.home.message.MessageTabFragment;
import com.example.crossingfield.home.mypage.MypageTabFragment;
import com.example.crossingfield.home.search.SearchContainerFragment;
import com.example.crossingfield.home.meeting.MeetingTabFragment;
import com.example.crossingfield.home.good.GoodTabFragment;

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

    private CharSequence[] tabTitles = {"待ち合わせ", "探す", "メッセージ", "相手から", "マイページ"};

    public HomeFragmentPagerAdapter(FragmentManager fm){
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
                return new MeetCrossTabFragment();
            case 1:
                return new SearchContainerFragment();
            case 2:
                return new MessageTabFragment();
            case 3:
                return new GoodTabFragment();
            case 4:
                return new MypageTabFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
