package com.example.lenovo.openeye_app.fragment.popular.presenter;

import com.example.lenovo.openeye_app.base.BasePresenter;
import com.example.lenovo.openeye_app.bean.BeanPopu;
import com.example.lenovo.openeye_app.fragment.popular.collback.WeekCollBack;
import com.example.lenovo.openeye_app.fragment.popular.model.WeekM;
import com.example.lenovo.openeye_app.fragment.popular.view.WeekV;

/**
 * Created by Lenovo on 2019/5/19.
 */

public class WeekP extends BasePresenter<WeekV> implements WeekCollBack{

    private WeekM model;

    @Override
    protected void initModel() {
        model = new WeekM();
        mModel.add(model);
    }

    public void getpopuDatas() {
        model.getpopuData(this);
    }

    @Override
    public void onSuccess(BeanPopu beanPopu) {
        if (mView!=null){
            mView.onSuccess(beanPopu);
        }
    }
}
