package com.example.lenovo.openeye_app.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.zip.Inflater;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Lenovo on 2019/5/15.
 */

public abstract class BaseFragment<V extends BaseView, P extends BasePresenter> extends Fragment implements BaseView {

    private Unbinder bind;
    public P mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(getLayoutId(), null);
        bind = ButterKnife.bind(this, inflate);
        mPresenter = initPresenter();
        if (mPresenter!=null){
            mPresenter.bint((V)this);
        }
        initView();
        initListener();
        return inflate;
    }

    protected abstract void initListener();

    protected abstract void initView();

    protected abstract P initPresenter();

    protected abstract int getLayoutId();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestory();
        mPresenter=null;
    }
}
