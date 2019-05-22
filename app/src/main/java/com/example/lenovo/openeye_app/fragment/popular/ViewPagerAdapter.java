package com.example.lenovo.openeye_app.fragment.popular;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2019/5/19.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fraglist;
    private ArrayList<String> list;
    private boolean isSlide = false;

    public ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fraglist, ArrayList<String> list) {
        super(fm);
        this.fraglist = fraglist;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fraglist.get(position);
    }

    @Override
    public int getCount() {
        return fraglist.size();
    }

    public void setSlide(boolean slide) {
        isSlide = slide;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}
