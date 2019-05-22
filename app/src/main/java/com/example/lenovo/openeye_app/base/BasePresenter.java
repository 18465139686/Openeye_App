package com.example.lenovo.openeye_app.base;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2019/5/15.
 */

public abstract class BasePresenter <V extends BaseView>{
    protected V mView;
    protected ArrayList<BaseModel> mModel=new ArrayList<>();

    public BasePresenter() {
        initModel();
    }

    protected abstract void initModel();

    public void bint(V v) {
        this.mView=v;
    }


    public void onDestory() {
        mView=null;
        if (mModel.size() > 0) {
            for(BaseModel model:mModel){
                model.onDestory();
            }
        }
    }
}
