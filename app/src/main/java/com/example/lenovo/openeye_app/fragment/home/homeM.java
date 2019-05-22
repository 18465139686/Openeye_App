package com.example.lenovo.openeye_app.fragment.home;

import android.util.Log;

import com.example.lenovo.openeye_app.RxUtil.BaseObserver;
import com.example.lenovo.openeye_app.RxUtil.HttpUtils;
import com.example.lenovo.openeye_app.RxUtil.RxUtils;
import com.example.lenovo.openeye_app.base.BaseModel;
import com.example.lenovo.openeye_app.bean.home.BeanHome;
import com.example.lenovo.openeye_app.util.ApiService;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Lenovo on 2019/5/15.
 */

public class homeM extends BaseModel{
    private static final String TAG = "homeM";

    public void gethome(int mPage, final homeCollBack collBack) {
        ApiService apiserver = HttpUtils.getInstance().getApiserver(ApiService.homeUrl, ApiService.class);
        Observable<BeanHome> gethome = apiserver.gethome(mPage);
        gethome.compose(RxUtils.<BeanHome>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<BeanHome>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompost.add(d);
                    }

                    @Override
                    public void onNext(BeanHome beanHome) {
                        Log.d(TAG, "onNext: "+beanHome.getIssueList().toString());
                        if (beanHome!=null){
                            collBack.onSuccess(beanHome);

                        }
                    }
                });
    }
}
