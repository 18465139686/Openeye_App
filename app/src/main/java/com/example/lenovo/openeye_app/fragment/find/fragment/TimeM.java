package com.example.lenovo.openeye_app.fragment.find.fragment;

import com.example.lenovo.openeye_app.RxUtil.BaseObserver;
import com.example.lenovo.openeye_app.RxUtil.HttpUtils;
import com.example.lenovo.openeye_app.RxUtil.RxUtils;
import com.example.lenovo.openeye_app.base.BaseModel;
import com.example.lenovo.openeye_app.base.BasePresenter;
import com.example.lenovo.openeye_app.util.ApiService;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Lenovo on 2019/5/20.
 */

public class TimeM extends BaseModel {

    public void getTime(final TimeCollBack collBack) {
        ApiService apiserver = HttpUtils.getInstance().getApiserver(ApiService.findTimeUrl, ApiService.class);
        Observable<BeanFindTime> beanFindTimeObservable = apiserver.getfindTime();
        beanFindTimeObservable.compose(RxUtils.<BeanFindTime>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<BeanFindTime>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompost.add(d);
                    }

                    @Override
                    public void onNext(BeanFindTime beanFindTime) {
                        if (beanFindTime!=null){
                            collBack.onSuccess(beanFindTime);
                        }
                    }
                });
    }
}
