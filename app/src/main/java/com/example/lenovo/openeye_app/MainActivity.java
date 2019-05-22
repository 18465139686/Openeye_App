package com.example.lenovo.openeye_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.openeye_app.activity.MenutActivity;
import com.example.lenovo.openeye_app.activity.WeatherActivity;
import com.example.lenovo.openeye_app.base.BaseActivity;
import com.example.lenovo.openeye_app.fragment.find.FindFragment;
import com.example.lenovo.openeye_app.fragment.home.HomeFragment;
import com.example.lenovo.openeye_app.fragment.popular.PopularFragment;
import com.example.lenovo.openeye_app.main.MainP;
import com.example.lenovo.openeye_app.main.MainV;
import com.example.lenovo.openeye_app.slide.ShareUtil;
import com.example.lenovo.openeye_app.utils.MyUtils;
import com.example.lenovo.openeye_app.widget.CustomTextView;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainV, MainP> implements MainV {


    @BindView(R.id.main_toolbar)
    Toolbar mainToolbar;
    @BindView(R.id.main_toolbar_tv_time)
    CustomTextView mainToolbarTvTime;
    @BindView(R.id.main_toolbar_iv_right)
    ImageButton mainToolbarIvRight;
    @BindView(R.id.tv_daily)
    TextView tvDaily;
    @BindView(R.id.tv_find)
    TextView tvFind;
    @BindView(R.id.tv_hot)
    TextView tvHot;
    @BindView(R.id.desc)
    TextView userName;
    @BindView(R.id.temp)
    TextView temp;
    @BindView(R.id.main_menu)
    LinearLayout mainMenu;
    @BindView(R.id.weather_rl)
    RelativeLayout weatherRl;
    @BindView(R.id.profile_image)
    SimpleDraweeView avatar;
    private FragmentManager mManager;
    private ViewGroup viewGroupup;
    private int choice;
    private FragmentTransaction transaction;
    private HomeFragment dailyFragment;
    private FindFragment morefragment;
    private PopularFragment hotFragment;
    private String Url="http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg";

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        mManager = getSupportFragmentManager();
        initViews();
    }

    private void initViews() {
        viewGroupup = findViewById(R.id.menu);
        MyUtils.setBackground(viewGroupup, this);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mainToolbar.setNavigationIcon(R.drawable.ic_action_menu);
        initUserIfto();
        setChoice(1);
    }

    private void initUserIfto() {

    }

    @Override
    protected MainP initPresenter() {
        return new MainP();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    /*
    * 设置监听事件
    * */
    @OnClick({R.id.tv_collect,R.id.tv_mydown,R.id.tv_fuli,R.id.tv_share,R.id.tv_feedback,R.id.tv_setting,R.id.about,R.id.theme,R.id.weather_rl,R.id.profile_image})
    public void  onClick1(View v) {
        switch (v.getId()) {
            case R.id.weather_rl:
                Intent intent = new Intent(this, WeatherActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title","天气查询");
                bundle.putString("url","http://m.weather.com.cn/mweather/101190201.shtml");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.theme:
                startMenuActivity("主题", 8);
                break;
            case R.id.about:
                startMenuActivity("关于",7);
                break;
            case R.id.tv_feedback:
                //startActivity(new Intent());
                break;
            case R.id.tv_mydown:
                //startActivity(new Intent());
                break;
            case R.id.tv_collect:
                //startActivity(new Intent());
                break;
            case R.id.tv_share:
                ShareUtil.shareText(MainActivity.this,Url,"分享");
                break;
            case R.id.profile_image:
                //startActivity(new Intent());
                break;
            case R.id.tv_fuli:
                //startActivity(new Intent());
                break;
        }
    }


    private void startMenuActivity(String name, int id) {
        Intent intent=new Intent(this,MenutActivity.class);
        intent.putExtra("theme",name);
        intent.putExtra("id",id);
        startActivity(intent);
    }


    //选择显示和隐藏
    public void setChoice(int choice) {
        transaction = mManager.beginTransaction();
        HideFragment(transaction);
        clearChioce();
        switch (choice){
            case 1:
                //每日精选
                mainToolbarTvTime.setVisibility(View.VISIBLE);
                mainToolbarIvRight.setImageResource(R.drawable.main_toolbar_eye_selector);
                tvDaily.setTextColor(getResources().getColor(R.color.colorBlack));
                if(dailyFragment==null){
                    dailyFragment = new HomeFragment();
                    transaction.add(R.id.main_ll_fragment, dailyFragment);
                }else{
                    transaction.show(dailyFragment);
                }
                break;
            case 2:
                //发现更多
                mainToolbarIvRight.setImageResource(R.drawable.ic_action_search);
                mainToolbarIvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, FindFragment.class));
                    }
                });
                mainToolbarTvTime.setVisibility(View.GONE);
                tvFind.setTextColor(getResources().getColor(R.color.colorBlack));
                if (morefragment==null){
                    morefragment = new FindFragment();
                    transaction.add(R.id.main_ll_fragment,morefragment);
                }else{//显示发现更多
                    transaction.show(morefragment);
                }
                break;
            case 3:
                //热门排行
                mainToolbarIvRight.setImageResource(R.drawable.main_toolbar_eye_selector);
                mainToolbarTvTime.setVisibility(View.GONE);
                tvHot.setTextColor(getResources().getColor(R.color.colorBlack));
                if (hotFragment==null){
                    hotFragment = new PopularFragment();
                    transaction.add(R.id.main_ll_fragment,hotFragment);
                }else{//显示热门排行
                    transaction.show(hotFragment);
                }
                break;
        }
        //提交事务
        transaction.commit();
    }

    private void clearChioce() {
        //还原默认选项
        tvDaily.setTextColor(getResources().getColor(R.color.colorGray));
        tvFind.setTextColor(getResources().getColor(R.color.colorGray));
        tvHot.setTextColor(getResources().getColor(R.color.colorGray));
    }

    //隐藏所有Fragment,避免混淆
    private void HideFragment(FragmentTransaction transaction) {
        if (dailyFragment!=null){
            transaction.hide(dailyFragment);
        }
        if (morefragment!=null){
            transaction.hide(morefragment);
        }
        if (hotFragment!=null){
            transaction.hide(hotFragment);
        }
    }

    @OnClick({R.id.tv_daily, R.id.tv_find, R.id.tv_hot})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_daily:
                setChoice(1);
                break;
            case R.id.tv_find:
                setChoice(2);
                break;
            case R.id.tv_hot:
                setChoice(3);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUserIfto();
    }



}
