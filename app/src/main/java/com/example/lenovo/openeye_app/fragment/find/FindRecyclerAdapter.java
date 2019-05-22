package com.example.lenovo.openeye_app.fragment.find;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.openeye_app.R;
import com.example.lenovo.openeye_app.bean.find.BeanFind;
import com.example.lenovo.openeye_app.beanUtil.FindMoreEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lenovo on 2019/5/17.
 */

public class FindRecyclerAdapter extends RecyclerView.Adapter {

    private List<BeanFind> list;
    private Context context;
    private setOnClickListener li;

    public FindRecyclerAdapter(List<BeanFind> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.find_adapter, null, false);
        return new HoldView(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        HoldView holdView= (HoldView) holder;
        holdView.mFindName.setText(list.get(position).getName());
        Glide.with(context).load(list.get(position).getBgPicture()).into(holdView.mFindImage);
        holdView.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (li!=null){
                    li.OnClickListener(position);
                }
            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<BeanFind> data) {
        list.addAll(data);
        notifyDataSetChanged();
    }


    class HoldView extends RecyclerView.ViewHolder {
        @BindView(R.id.find_image)
        ImageView mFindImage;
        @BindView(R.id.find_name)
        TextView mFindName;
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
