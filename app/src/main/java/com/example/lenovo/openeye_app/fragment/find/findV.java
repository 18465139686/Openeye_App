package com.example.lenovo.openeye_app.fragment.find;

import com.example.lenovo.openeye_app.base.BaseView;
import com.example.lenovo.openeye_app.bean.find.BeanFind;

import java.util.List;

/**
 * Created by Lenovo on 2019/5/15.
 */

public interface findV extends BaseView {

    void onSuccess(List<BeanFind> beanFinds);
}
