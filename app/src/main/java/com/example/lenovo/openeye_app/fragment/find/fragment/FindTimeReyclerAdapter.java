package com.example.lenovo.openeye_app.fragment.find.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.openeye_app.R;
import com.example.lenovo.openeye_app.bean.BeanPopu;
import com.example.lenovo.openeye_app.widget.CustomTextView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lenovo on 2019/5/19.
 */

public class FindTimeReyclerAdapter extends RecyclerView.Adapter {
    public ArrayList<BeanFindTime.ItemListBeanX.DataBeanX.ItemListBean> list;
    private Context context;
    private setOnClickListener li;

    public FindTimeReyclerAdapter(ArrayList<BeanFindTime.ItemListBeanX.DataBeanX.ItemListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == 0) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.findtime_adapter, null, false);
            viewHolder = new HoldView(inflate);
       }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof HoldView) {
            HoldView holdView = (HoldView) holder;
            BeanFindTime.ItemListBeanX.DataBeanX.ItemListBean beanlist = list.get(position);
            holdView.mTvTitle.setText(beanlist.getData().getTitle());


            //获取时间
            int duration = beanlist.getData().getDuration();
            int mm = duration / 60;//分
            int ss = duration % 60;//秒
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
            holdView.mTvTime.setText("#" + beanlist.getData().getCategory() + " / " + minute + "'" + second + '"');

            Glide.with(context).load(beanlist.getData().getCover().getFeed()).into(holdView.mIv);

            holdView.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (li!=null){
                        li.OnClickListener(position);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(ArrayList<BeanFindTime.ItemListBeanX.DataBeanX.ItemListBean> data) {
        this.list = data;
    }

    class HoldView extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        SimpleDraweeView mIv;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.rl_text)
        RelativeLayout mRlText;
        @BindView(R.id.ll_moban)
        LinearLayout mLlMoban;

        public HoldView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnClickListen(setOnClickListener li){
        this.li=li;
    }

    public interface setOnClickListener{
        void OnClickListener(int position);
    }


}
