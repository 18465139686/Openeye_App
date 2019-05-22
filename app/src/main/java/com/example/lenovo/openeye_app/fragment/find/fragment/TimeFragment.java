package com.example.lenovo.openeye_app.fragment.find.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.openeye_app.R;
import com.example.lenovo.openeye_app.activity.HomeActivity;
import com.example.lenovo.openeye_app.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class
TimeFragment extends BaseFragment<TimeV, TimeP> implements TimeV {


    @BindView(R.id.times_rlv)
    RecyclerView mTimesRlv;
    private View view;
    private Unbinder unbinder;
    private ArrayList<BeanFindTime.ItemListBeanX.DataBeanX.ItemListBean> list=new ArrayList<>();
    private FindTimeReyclerAdapter adapter;


    @Override
    protected void initListener() {
        mTimesRlv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new FindTimeReyclerAdapter(list,getContext());
        mTimesRlv.setAdapter(adapter);

        adapter.setOnClickListen(new FindTimeReyclerAdapter.setOnClickListener() {
            @Override
            public void OnClickListener(int position) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                Bundle bundle = new Bundle();
                Log.i("--->", position + "");
                BeanFindTime.ItemListBeanX.DataBeanX.ItemListBean.DataBean data = list.get(position).getData();
                if (!"video".equals(list.get(position).getType())){
                    return;
                }
                //获取标题
                bundle.putString("title",data.getTitle());
                //获取视频时间
                int duration = data.getDuration();
                int mm =duration/60;
                int ss = duration%60;
                String second = "";//秒
                String minute = "";//分
                if (ss < 10) {
                    second = "0" + String.valueOf(ss);
                } else {
                    second = String.valueOf(ss);
                }
                if (mm < 10) {
                    minute = "0" + String.valueOf(mm);
                } else {
                    minute = String.valueOf(mm);//分钟
                }

                bundle.putString("time", "#" + data.getCategory() + " / " + minute + "'" + second + '"');
                bundle.putString("desc", data.getDescription());//视频描述
                bundle.putString("blurred", data.getCover().getBlurred());//模糊图片地址
                bundle.putString("feed", data.getCover().getFeed());//图片地址
                bundle.putString("video", data.getPlayUrl());//视频播放地址
                bundle.putInt("collect", data.getConsumption().getCollectionCount());//收藏量
                bundle.putInt("share", data.getConsumption().getShareCount());//分享量
                bundle.putInt("reply", data.getConsumption().getReplyCount());//回复数量
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initView() {
        mPresenter.getTime();
    }

    @Override
    protected TimeP initPresenter() {
        return new TimeP();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_time;
    }

    @Override
    public void onSuccess(BeanFindTime beanFindTime) {
        list.addAll(beanFindTime.getItemList().get(0).getData().getItemList());
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }


}
