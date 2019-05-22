package com.example.lenovo.openeye_app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lenovo.openeye_app.R;
import com.example.lenovo.openeye_app.utils.PreViewGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.Random;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

public class MoreShowActivity extends GSYBaseActivityDetail<StandardGSYVideoPlayer> implements View.OnClickListener {
    private PreViewGSYVideoPlayer mPlayvideo;
    private int backupRendType;
    private String video;
    private String title;
    private DanmakuView mDanmakuView;
    private boolean showDanmaku;
    private DanmakuContext danmakuContext;

    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };
    private EditText mEditText;
    /**
     * Send
     */
    private Button mSend;
    private LinearLayout mOperationLayout;
    /**
     * 弹幕
     */
    private TextView mTanTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_show);
        initView();
        backupRendType = GSYVideoType.getRenderType();

        //设置为Surface播放模式，注意此设置是全局的
        GSYVideoType.setRenderType(GSYVideoType.SUFRACE);

        resolveNormalVideoUI();

        initVideoBuilderMode();

        mPlayvideo.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                    mPlayvideo.getCurrentPlayer().setRotateViewAuto(!lock);
                }
            }
        });


        //设置返回按键功能
        mPlayvideo.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        orientationUtils.setRotateWithSystem(true);


        mTanTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOperationLayout.getVisibility() == View.GONE) {
                    mOperationLayout.setVisibility(View.VISIBLE);
                } else {
                    mOperationLayout.setVisibility(View.GONE);
                }
            }
        });
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = mEditText.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    addDanmaku(content, true);
                    mEditText.setText("");

                }
            }
        });
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == View.SYSTEM_UI_FLAG_VISIBLE) {
                    onWindowFocusChanged(true);

                }
            }
        });
    }

    @Override
    public StandardGSYVideoPlayer getGSYVideoPlayer() {
        return mPlayvideo;
    }


    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        //增加封面。内置封面可参考SampleCoverVideo
        ImageView imageView = new ImageView(this);
        loadCover(imageView, video);
        return new GSYVideoOptionBuilder()
                .setThumbImageView(imageView)
                .setUrl(video)
                .setCacheWithPlay(false)
                .setRotateWithSystem(true)    //设置旋转
                .setVideoTitle(title)
                .setIsTouchWiget(true)         //设置是否触摸
                .setRotateViewAuto(false)       //设置旋转的视图
                .setLockLand(false)             //设置锁土地
                .setShowFullAnimation(false)    // 检测
                .setNeedLockFull(true);         //设备需要锁安全
    }

    private void loadCover(ImageView imageView, String url) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.nn);

        Glide.with(this.getApplicationContext())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(3000000)
                                .centerCrop()
                                .error(R.mipmap.nn)
                                .placeholder(R.mipmap.nn))
                .load(url)
                .into(imageView);
    }

    private void resolveNormalVideoUI() {
        //增加title
        mPlayvideo.getTitleTextView().setVisibility(View.GONE);
        mPlayvideo.getBackButton().setVisibility(View.GONE);
    }

    @Override
    public void clickForFullScreen() {

    }

    @Override
    public boolean getDetailOrientationRotateAuto() {
        return true;
    }

    private void initView() {
        Intent intent = getIntent();
        video = intent.getStringExtra("video");
        title = intent.getStringExtra("title");

        mPlayvideo = (PreViewGSYVideoPlayer) findViewById(R.id.pregsyv);

        mPlayvideo.startAfterPrepared();

        mDanmakuView = (DanmakuView) findViewById(R.id.danmaku_view);

        mDanmakuView.enableDanmakuDrawingCache(true);
        mDanmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                showDanmaku = true;
                mDanmakuView.start();
                generateSomeDanmaku();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        danmakuContext = DanmakuContext.create();
        mDanmakuView.prepare(parser, danmakuContext);

        mEditText = (EditText) findViewById(R.id.edit_text);
        mSend = (Button) findViewById(R.id.send);
        mSend.setOnClickListener(this);
        mOperationLayout = (LinearLayout) findViewById(R.id.operation_layout);
        mTanTitle = (TextView) findViewById(R.id.tan_title);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 向弹幕View中添加一条弹幕
     *
     * @param content    弹幕的具体内容
     * @param withBorder 弹幕是否有边框
     */
    private void addDanmaku(String content, boolean withBorder) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = sp2px(20);
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(mDanmakuView.getCurrentTime());
        if (withBorder) {
            danmaku.borderColor = Color.GREEN;
        }
        mDanmakuView.addDanmaku(danmaku);
    }

    /**
     * 随机生成一些弹幕内容以供测试
     */
    private void generateSomeDanmaku() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (showDanmaku) {
                    int time = new Random().nextInt(300);
                    String content = "" + time + time;
                    addDanmaku(content, false);
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

  /*  native  graphics   stack    code   others
    本地的     图形       堆栈     代码    其他 */

    /**
     * sp转px的方法。
     */
    public int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //设置为GL播放模式，才能支持滤镜，注意此设置是全局的
        GSYVideoType.setRenderType(backupRendType);
        showDanmaku = false;
        if (mDanmakuView != null) {
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }


    //沉浸式状态
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.send:
                break;
        }
    }
}
