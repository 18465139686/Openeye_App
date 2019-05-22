package com.example.lenovo.openeye_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.example.lenovo.openeye_app.R;
import com.example.lenovo.openeye_app.fragment.find.fragment.ShareItFragment;
import com.example.lenovo.openeye_app.fragment.find.fragment.TimeFragment;
import com.example.lenovo.openeye_app.fragment.popular.ViewPagerAdapter;
import com.example.lenovo.openeye_app.widget.CustomTextView;
import com.example.lenovo.openeye_app.widget.CustomViewPager;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindHomeActivity extends AppCompatActivity {


    @BindView(R.id.viewpagertabs)
    SmartTabLayout mViewpagertab;
    @BindView(R.id.hot_viewpagers)
    CustomViewPager mHotViewpager;
    @BindView(R.id.find_name)
    CustomTextView mFindName;
    @BindView(R.id.main_toolbar_iv_right)
    ImageButton mMainToolbarIvRight;
    @BindView(R.id.find_toolbar)
    Toolbar mFindToolbar;
    private ArrayList<Fragment> fraglist = new ArrayList<>();
    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_home);
        ButterKnife.bind(this);

        Intent intent=getIntent();
        String title = intent.getStringExtra("name");

        initFragment();
        initData();


        mFindName.setText("");
        //Activity绑定Toolbar
        setSupportActionBar(mFindToolbar);
//设置原标题消失
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//设置Toolbar返回自带按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFindToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mFindName.setText(title);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fraglist, list);
        mHotViewpager.setAdapter(adapter);
        mViewpagertab.setViewPager(mHotViewpager);
        mHotViewpager.setOffscreenPageLimit(3);//设置缓冲的页面数量

    }

    private void initData() {
        list.add("按时间排序");
        list.add("分享排序");
    }

    private void initFragment() {
        fraglist.add(new TimeFragment());
        fraglist.add(new ShareItFragment());
    }

    @OnClick({R.id.main_toolbar_iv_right})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.main_toolbar_iv_right:
                break;
        }
    }
}
