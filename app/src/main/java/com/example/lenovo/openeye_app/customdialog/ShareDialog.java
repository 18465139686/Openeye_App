package com.example.lenovo.openeye_app.customdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.lenovo.openeye_app.MainActivity;
import com.example.lenovo.openeye_app.R;


import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by darryrzhong on 2018/9/3.
 */

public class ShareDialog extends Dialog {

    private LayoutInflater inflater;
    private Context context;
    private shareListener shareListener;
    private int layoutId;

    public interface shareListener{
        void shareName(String fromname, Context context);
    }

    public ShareDialog(@NonNull Context context,int layoutId, int themeResId,shareListener shareListener) {
        super(context, themeResId);
        this.inflater = LayoutInflater.from(context);
        this.context= context;
        this.layoutId =layoutId;
        this.shareListener = shareListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(layoutId,null);
        setContentView(view);
        ButterKnife.bind(this,view);
        init();
    }

    private void init() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        params.width = context.getResources().getDisplayMetrics().widthPixels;
        dialogWindow.setAttributes(params);
        dialogWindow.setGravity(Gravity.BOTTOM);
    }

    /**
    * 监听第三方分享
    * */
    @OnClick({R.id.share_wechat,R.id.share_weibo,R.id.share_qq})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.share_wechat:
                //share(SHARE_MEDIA.WEIXIN);
                dismiss();
                break;
            case R.id.share_weibo:
                //share(SHARE_MEDIA.SINA);
                dismiss();
                break;
            case R.id.share_qq:
               // share(SHARE_MEDIA.QQ);
                dismiss();
                break;

        }
    }

  /*  public void share(SHARE_MEDIA type) {
        UMImage thumb = new UMImage(context,"https://ws1.sinaimg.cn/large/0065oQSqly1g0ajj4h6ndj30sg11xdmj.jpg");
        thumb.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，
        new ShareAction(getOwnerActivity())
                .setPlatform(type)//传入平台
                .withText("hello")//分享内容
                .withMedia(thumb)//图片
                .setCallback(shareListeners)//回调监听器
                .share();
    }

    private UMShareListener shareListeners = new UMShareListener() {
        *//**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         *//*
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        *//**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         *//*
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(getOwnerActivity(), "成功了", Toast.LENGTH_LONG).show();
        }

        *//**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         *//*
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getOwnerActivity(), "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        *//**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         *//*
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getOwnerActivity(), "取消了", Toast.LENGTH_LONG).show();

        }
    };
*/
}
