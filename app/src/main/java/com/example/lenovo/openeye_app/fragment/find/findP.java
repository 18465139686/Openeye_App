package com.example.lenovo.openeye_app.fragment.find;

import com.example.lenovo.openeye_app.base.BasePresenter;
import com.example.lenovo.openeye_app.bean.find.BeanFind;

import java.util.List;

/**
 * Created by Lenovo on 2019/5/15.
 */

public class findP extends BasePresenter<findV> implements findCollBack{

    private findM model;

    @Override
    protected void initModel() {
        model = new findM();
        mModel.add(model);
    }

    public void getFind() {
        model.getfind(this);
    }


    @Override
    public void onSuccess(List<BeanFind> beanFinds) {
        if (mView!=null){
            mView.onSuccess(beanFinds);
        }
    }
}
