package com.example.lenovo.openeye_app.fragment.home;

import com.example.lenovo.openeye_app.base.BasePresenter;
import com.example.lenovo.openeye_app.bean.home.BeanHome;

/**
 * Created by Lenovo on 2019/5/15.
 */

public class homeP extends BasePresenter<homeV> implements homeCollBack{

    private homeM model;

    @Override
    protected void initModel() {
        model = new homeM();
        mModel.add(model);
    }

    public void gethome(int mPage) {
        model.gethome(mPage,this);
    }

    @Override
    public void onSuccess(BeanHome beanHome) {
        if (mView!=null){
            mView.onSuccess(beanHome);
        }
    }
}
