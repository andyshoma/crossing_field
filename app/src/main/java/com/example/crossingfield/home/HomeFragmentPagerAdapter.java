package com.example.crossingfield.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.crossingfield.home.meeting.MeetCrossTabFragment;
import com.example.crossingfield.home.message.MessageTabFragment;
import com.example.crossingfield.home.mypage.MypageTabFragment;
import com.example.crossingfield.home.search.SearchContainerFragment;
import com.example.crossingfield.home.meeting.MeetingTabFragment;
import com.example.crossingfield.home.good.GoodTabFragment;
import com.example.crossingfield.home.search.SearchTabFragment;
import com.example.crossingfield.lib.User;

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

    private CharSequence[] tabTitles = {"待ち合わせ", "探す", "メッセージ", "相手から", "マイページ"};
    private Bundle bundle;

    public HomeFragmentPagerAdapter(FragmentManager fm, User user){
        super(fm);

        bundle = new Bundle();
        bundle.putSerializable("my_user", user);
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

    public void setUserToFragment(User user){
        bundle = new Bundle();
        bundle.putSerializable("my_user", user);
    }
}
