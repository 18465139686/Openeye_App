package com.example.lenovo.openeye_app.fragment.find;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.openeye_app.R;
import com.example.lenovo.openeye_app.activity.FindHomeActivity;
import com.example.lenovo.openeye_app.base.BaseFragment;
import com.example.lenovo.openeye_app.bean.find.BeanFind;
import com.example.lenovo.openeye_app.beanUtil.FindMoreEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Lenovo on 2019/5/15.
 */

public class FindFragment extends BaseFragment<findV, findP> implements findV {
    private static final String TAG = "FindFragment";
    @BindView(R.id.find_rlv)
    RecyclerView mFindRlv;

    private List<FindMoreEntity> moreEntities = new ArrayList<>();
    private List<BeanFind> list=new ArrayList<>();
    private View view;
    private Unbinder unbinder;
    private FindRecyclerAdapter adapter1;

    @Override
    protected void initListener() {
        GridLayoutManager manager=new GridLayoutManager(getContext(),2);
        mFindRlv.setLayoutManager(manager);

        adapter1 = new FindRecyclerAdapter(list,getContext());
        mFindRlv.setAdapter(adapter1);

        adapter1.setOnClickListen(new FindRecyclerAdapter.setOnClickListener() {
            @Override
            public void OnClickListener(int position) {
                BeanFind beanFind=list.get(position);
                Intent intent=new Intent(getContext(),FindHomeActivity.class);
                intent.putExtra("name", beanFind.getName());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView() {
        mPresenter.getFind();
    }


    @Override
    protected findP initPresenter() {
        return new findP();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }


    @Override
    public void onSuccess(List<BeanFind> beanFinds) {
        adapter1.setData(beanFinds);
    }
}
