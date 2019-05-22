package com.example.lenovo.openeye_app.fragment.popular;

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

public class PopuReyclerAdapter extends RecyclerView.Adapter {
    public ArrayList<BeanPopu.ItemListBean> list;
    private Context context;
    private final int[] i = {0};//设置数据的序号
    private setOnClickListener li;

    public PopuReyclerAdapter(ArrayList<BeanPopu.ItemListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0) {
//            return 1;
//        } else {
//            return 2;
//        }
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == 0) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.popu_adapter, null, false);
            viewHolder = new HoldView(inflate);
       }
// else if (viewType == 1) {
//            View inflate = LayoutInflater.from(context).inflate(R.layout.popu_adapter2, null, false);
//           // viewHolder = new HoldView02(inflate);
//        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof HoldView) {
            HoldView holdView = (HoldView) holder;
            BeanPopu.ItemListBean beanlist = list.get(position);
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
            if (i[0] < list.size()) {
                holdView.mHotTvTextnumber.setText(++i[0] + ".");
            }

            holdView.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (li!=null){
                        li.OnClickListener(position);
                    }
                }
            });

        }
     /*   else if (holder instanceof HoldView02) {
            HoldView02 holdView02= (HoldView02) holder;
            holdView02.mPopuViewtext.setText("-The End-");
        }*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(ArrayList<BeanPopu.ItemListBean> data) {
        this.list = data;
    }

  /*  class HoldView02 extends RecyclerView.ViewHolder {
        @BindView(R.id.popu_viewtext)
        CustomTextView mPopuViewtext;
        public HoldView02(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }*/


    class HoldView extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        SimpleDraweeView mIv;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.hot_iv_textup)
        ImageView mHotIvTextup;
        @BindView(R.id.hot_tv_textnumber)
        CustomTextView mHotTvTextnumber;
        @BindView(R.id.hot_iv_textdown)
        ImageView mHotIvTextdown;
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
