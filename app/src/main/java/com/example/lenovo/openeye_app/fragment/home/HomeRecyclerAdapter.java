package com.example.lenovo.openeye_app.fragment.home;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.lenovo.openeye_app.R;
import com.example.lenovo.openeye_app.bean.home.BeanHome;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lenovo on 2019/5/16.
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter {

    public List<BeanHome.IssueListEntity.ItemListEntity> list;
    private Context context;
    private setOnClickListener li;

    public HomeRecyclerAdapter(List<BeanHome.IssueListEntity.ItemListEntity> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 2;
        } else {
            return 1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == 1) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.home01_adapter, null, false);
            viewHolder = new HoldView(inflate);
        } else {
            View inflate = LayoutInflater.from(context).inflate(R.layout.home02_adapter, null, false);
            viewHolder = new HoldListView(inflate);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        BeanHome.IssueListEntity.ItemListEntity itemListBean = list.get(position);
        int itemViewType = getItemViewType(position);

        String feed = "1";
        String title = "1";
        String category = "1";
        int duration = 0;
        String text = "1";

        if (itemViewType==1){
            HoldView holdView= (HoldView) holder;
            //获取相关信息
           if (itemListBean!=null&&itemListBean.getData()!=null&&itemListBean.getData().getCover()!=null&&itemListBean.getData().getCover().getFeed()!=null){
                feed=itemListBean.getData().getCover().getFeed();
            }

            title = itemListBean.getData().getTitle();
            category =itemListBean.getData().getCategory();
            category = "#" + category + "  /  ";
            duration = itemListBean.getData().getDuration();

            int last = duration % 60;
            String stringLast;
            if (last <= 9) {
                stringLast = "0" + last;
            } else {
                stringLast = last + "";
            }

            //获取视频时间
            String durationString;
            int minit = duration / 60;
            if (minit < 10) {
                durationString = "0" + minit;
            } else {
                durationString = "" + minit;
            }
            String stringTime = durationString + "' " + stringLast + '"';

           // 设置数据
            Uri uri = Uri.parse(feed);
            holdView.mImage.setImageURI(uri);
         //Glide.with(context).load(itemListBean.getData().getCover().getFeed()).into(holdView.mImage);
            holdView.mTvTitle.setText(title);
            holdView.mTvTime.setText(category + stringTime);

            holdView.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (li!=null){
                        li.OnClickListener(position);
                    }
                }
            });
        }else{
            HoldListView holdListView= (HoldListView) holder;
            text = itemListBean.getData().getTitle();
            holdListView.mTvHomeText.setText(text);

            String image = list.get(position).getData().getImage();
            if (!TextUtils.isEmpty(image)) {
                holdListView.mTvHomeText.setTextSize(20);
                holdListView.mTvHomeText.setText("-Weekend  special-");
                holdListView.mTvHomeTexts.setTextSize(20);
                holdListView.mTvHomeTexts.setText("-Weekend  special-");
            }

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<BeanHome.IssueListEntity.ItemListEntity> data) {
        this.list = data;
    }


    class HoldView extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.rl_text)
        RelativeLayout mRlText;
        @BindView(R.id.ll_moban)
        LinearLayout mLlMoban;
        @BindView(R.id.home_image)
        ImageView mImage;

        public HoldView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HoldListView extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_home_text)
        TextView mTvHomeText;
        @BindView(R.id.tv_home_texts)
        TextView mTvHomeTexts;
        public HoldListView(View itemView) {
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
