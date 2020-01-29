package com.wemsuser.app.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wemsuser.app.Fragment.BookMarkFragment;
import com.wemsuser.app.Fragment.CarListFragment;

import java.util.ArrayList;
import java.util.List;

public class Pager extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public Pager(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);

//        switch (i) {
//            case 0:
//                CarListFragment tab1 = new CarListFragment();
//                return tab1;
//            case 1:
//                BookMarkFragment tab2 = new BookMarkFragment();
//                return tab2;
//            default:
//                return null;
//        }
    }
    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public int getCount() {
        return mFragmentList.size() ;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
