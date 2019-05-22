package com.example.lenovo.openeye_app.fragment.popular.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.openeye_app.R;
import com.example.lenovo.openeye_app.activity.HomeActivity;
import com.example.lenovo.openeye_app.activity.PopularActivity;
import com.example.lenovo.openeye_app.base.BaseFragment;
import com.example.lenovo.openeye_app.bean.BeanPopu;
import com.example.lenovo.openeye_app.fragment.popular.PopuReyclerAdapter;
import com.example.lenovo.openeye_app.fragment.popular.presenter.WeekP;
import com.example.lenovo.openeye_app.fragment.popular.view.WeekV;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class TotalFragment extends BaseFragment<WeekV, WeekP> implements WeekV {

    @BindView(R.id.tota_rlv)
    RecyclerView mPopuRlv;
    private View view;
    private Unbinder unbinder;
    private ArrayList<BeanPopu.ItemListBean> list=new ArrayList<>();
    private PopuReyclerAdapter adapter;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {

        mPresenter.getpopuDatas();

        mPopuRlv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PopuReyclerAdapter(list,getContext());
        mPopuRlv.setAdapter(adapter);

        adapter.setOnClickListen(new PopuReyclerAdapter.setOnClickListener() {
            @Override
            public void OnClickListener(int position) {
                Intent intent=new Intent(getContext(),HomeActivity.class);
                Bundle bundle=new Bundle();
                BeanPopu.ItemListBean.DataBean dataBean=list.get(position).getData();

                bundle.putString("title",dataBean.getTitle());

                //获取到时间
                int duration =dataBean.getDuration();
                int mm=duration/60;  //分
                int ss=duration%60;  //秒
                String second=""; //秒
                String minute=""; //分
                if (ss<10){
                    second="0" +String.valueOf(ss);
                }else{
                    second=String.valueOf(ss);
                }

                if (mm<10){
                    minute="0" +String.valueOf(mm);
                }else{
                    minute=String.valueOf(mm); //分钟
                }

                bundle.putString("time","#"+dataBean.getCategory()+" / "+minute+"'"+second+'"');
                bundle.putString("desc",dataBean.getDescription());  //获取视频描述
                bundle.putString("blurred",dataBean.getCover().getBlurred());  //模糊图片地址
                bundle.putString("feed",dataBean.getCover().getFeed()); //图片地址
                bundle.putString("video",dataBean.getPlayUrl());  //视频播放地址
                bundle.putInt("collect",dataBean.getConsumption().getCollectionCount());  //收藏量
                bundle.putInt("share",dataBean.getConsumption().getShareCount()); //分享量
                bundle.putInt("reply",dataBean.getConsumption().getReplyCount()); //回复数量
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    protected WeekP initPresenter() {
        return new WeekP();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_total;
    }

    @Override
    public void onSuccess(BeanPopu beanPopu) {
        list.addAll(beanPopu.getItemList());
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }
}
