package com.example.lenovo.openeye_app.fragment.find.fragment;

import com.example.lenovo.openeye_app.base.BasePresenter;
import com.example.lenovo.openeye_app.base.BaseView;

/**
 * Created by Lenovo on 2019/5/20.
 */

public class TimeP extends BasePresenter<TimeV> implements TimeCollBack{

    private TimeM model;

    @Override
    protected void initModel() {
        model = new TimeM();
        mModel.add(model);
    }

    public void getTime() {
        model.getTime(this);
    }

    @Override
    public void onSuccess(BeanFindTime beanFindTime) {
        if (mView!=null){
            mView.onSuccess(beanFindTime);
        }
    }
}
