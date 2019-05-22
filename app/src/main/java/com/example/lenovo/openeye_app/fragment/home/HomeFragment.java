package com.example.lenovo.openeye_app.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.openeye_app.R;
import com.example.lenovo.openeye_app.activity.HomeActivity;
import com.example.lenovo.openeye_app.base.BaseFragment;
import com.example.lenovo.openeye_app.bean.home.BeanHome;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Lenovo on 2019/5/15.
 */

public class HomeFragment extends BaseFragment<homeV, homeP> implements homeV {

    @BindView(R.id.home_rlv)
    RecyclerView mHomeRlv;

    private View view;
    private int mPage = 83;
    private Unbinder unbinder;
    private List<BeanHome.IssueListEntity.ItemListEntity> list = new ArrayList<>();
    private HomeRecyclerAdapter adapter;

    @Override
    protected void initListener() {
        mHomeRlv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new HomeRecyclerAdapter(list, getContext());
        mHomeRlv.setAdapter(adapter);



        adapter.setOnClickListen(new HomeRecyclerAdapter.setOnClickListener() {
            @Override
            public void OnClickListener(int position) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                Bundle bundle = new Bundle();
                Log.i("--->", position + "");
                BeanHome.IssueListEntity.ItemListEntity.DataEntity data = list.get(position).getData();
                if (!"video".equals(list.get(position).getType())) {
                    return;
                }
                //获取标题
                bundle.putString("title", data.getTitle());
                //获取视频时间
                int duration = data.getDuration();
                int mm = duration / 60;
                int ss = duration % 60;
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
        mPresenter.gethome(mPage);
    }

    @Override
    protected homeP initPresenter() {
        return new homeP();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    public void onSuccess(BeanHome beanHome) {
        list.addAll(beanHome.getIssueList().get(0).getItemList());
        adapter.setData(list);
        adapter.notifyDataSetChanged();

    }


}
