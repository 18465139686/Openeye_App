package com.example.lenovo.openeye_app.fragment.popular.model;

import android.util.Log;

import com.example.lenovo.openeye_app.RxUtil.BaseObserver;
import com.example.lenovo.openeye_app.RxUtil.HttpUtils;
import com.example.lenovo.openeye_app.RxUtil.RxUtils;
import com.example.lenovo.openeye_app.base.BaseModel;
import com.example.lenovo.openeye_app.bean.BeanPopu;
import com.example.lenovo.openeye_app.fragment.popular.collback.WeekCollBack;
import com.example.lenovo.openeye_app.fragment.popular.presenter.WeekP;
import com.example.lenovo.openeye_app.util.ApiService;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Lenovo on 2019/5/19.
 */

public class WeekM extends BaseModel {
    private static final String TAG = "BaseModel";

    public void getpopuData(final WeekCollBack collBack) {
        ApiService apiserver = HttpUtils.getInstance().getApiserver(ApiService.popuUrl, ApiService.class);
        Observable<BeanPopu> getpopu = apiserver.getpopu();
        getpopu.compose(RxUtils.<BeanPopu>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<BeanPopu>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompost.add(d);
                    }

                    @Override
                    public void onNext(BeanPopu beanPopu) {
                        Log.d(TAG, "onNext: "+beanPopu.getItemList().toString());
                        if (beanPopu!=null){
                            collBack.onSuccess(beanPopu);
                        }
                    }
                });
    }
}
