package com.example.lenovo.openeye_app.fragment.find;

import com.example.lenovo.openeye_app.RxUtil.BaseObserver;
import com.example.lenovo.openeye_app.RxUtil.HttpUtils;
import com.example.lenovo.openeye_app.RxUtil.RxUtils;
import com.example.lenovo.openeye_app.base.BaseModel;
import com.example.lenovo.openeye_app.bean.find.BeanFind;
import com.example.lenovo.openeye_app.bean.home.BeanHome;
import com.example.lenovo.openeye_app.util.ApiService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Lenovo on 2019/5/15.
 */

public class findM extends BaseModel{
    public void getfind(final findCollBack collBack) {
        ApiService apiserver = HttpUtils.getInstance().getApiserver(ApiService.findUrl, ApiService.class);
        Observable<List<BeanFind>> getfind = apiserver.getfind();
        getfind.compose(RxUtils.<List<BeanFind>>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<List<BeanFind>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompost.add(d);
                    }

                    @Override
                    public void onNext(List<BeanFind> beanFinds) {
                        if (beanFinds!=null){
                            collBack.onSuccess(beanFinds);
                        }
                    }
                });
    }
}


