package com.example.lenovo.openeye_app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Lenovo on 2019/5/15.
 */

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter> extends AppCompatActivity implements BaseView{

    public P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        mPresenter = initPresenter();
        if (mPresenter!=null){
            mPresenter.bint((V)this);
        }

        initView();
        initListener();
    }

    protected abstract void initListener();

    protected abstract void initView();

    protected abstract P initPresenter();

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestory();
        mPresenter=null;
    }
}
