package com.example.lenovo.openeye_app.fragment.popular;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.openeye_app.R;
import com.example.lenovo.openeye_app.base.BaseFragment;
import com.example.lenovo.openeye_app.fragment.popular.Fragment.MonthFragment;
import com.example.lenovo.openeye_app.fragment.popular.Fragment.TotalFragment;
import com.example.lenovo.openeye_app.fragment.popular.Fragment.WeekFragment;
import com.example.lenovo.openeye_app.widget.CustomViewPager;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Lenovo on 2019/5/15.
 */

public class PopularFragment extends BaseFragment<PopV, PopP> implements PopV {


    @BindView(R.id.viewpagertab)
    SmartTabLayout mViewpagertab;
    @BindView(R.id.hot_viewpager)
    CustomViewPager mHotViewpager;
    private ArrayList<Fragment> fraglist = new ArrayList<>();
    private ArrayList<String> list = new ArrayList<>();


    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        initTitle();
        FragmentData();

        ViewPagerAdapter adapter=new ViewPagerAdapter(getChildFragmentManager(),fraglist,list);
        mHotViewpager.setAdapter(adapter);
        mViewpagertab.setViewPager(mHotViewpager);
        mHotViewpager.setOffscreenPageLimit(3);//设置缓冲的页面数量

    }

    private void initTitle() {
        list.add("周排行");
        list.add("月排行");
        list.add("总排行");
    }

    private void FragmentData() {
        fraglist.add(new WeekFragment());
        fraglist.add(new MonthFragment());
        fraglist.add(new TotalFragment());
    }

    @Override
    protected PopP initPresenter() {
        return new PopP();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_popula;
    }


}
