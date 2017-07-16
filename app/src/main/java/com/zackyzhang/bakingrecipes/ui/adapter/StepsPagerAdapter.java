package com.zackyzhang.bakingrecipes.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by lei on 7/13/17.
 */

public class StepsPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList;
    private String[] indices;

    public StepsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragmentList = fragments;
        int length = mFragmentList.size();
        indices = new String[length];
        for (int i = 0; i < length; i++) {
            indices[i] = String.valueOf(i + 1);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return indices[position];
    }
}
