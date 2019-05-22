package com.example.lenovo.openeye_app.base;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Lenovo on 2019/5/15.
 */

public class BaseModel {
    public CompositeDisposable mCompost=new CompositeDisposable();

    public void onDestory() {
        mCompost.clear();
    }
}
