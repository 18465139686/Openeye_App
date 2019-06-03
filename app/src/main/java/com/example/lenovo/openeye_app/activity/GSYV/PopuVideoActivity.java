package com.example.lenovo.openeye_app.activity.GSYV;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

public class PopuVideoActivity extends GSYBaseActivityDetail<StandardGSYVideoPlayer> implements View.OnClickListener {
    private PreViewGSYVideoPlayer mPlayvideo;
    private int backupRendType;
    private String video;
    private String title;


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

    private mSurfaceView msurfaceView;
    private Random random=new Random();

    private String[] strings={"6666","厉害了我的国","加油！！！","欢迎收看晨间新闻","程序猿很苦逼","我能怎么办，我也很无奈"};

    private int[] colors={Color.WHITE,Color.MAGENTA,Color.CYAN,Color.RED,Color.BLUE,Color.GREEN};
    private DanmakuContext danmakuContext;
    private com.example.lenovo.openeye_app.activity.GSYV.mText mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popu_video);
        Intent intent = getIntent();
        video = intent.getStringExtra("video");
        title = intent.getStringExtra("title");
        initView();

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

        orientationUtils.setRotateWithSystem(true);  //设置旋转

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

    //设置规模类型
    //设置进入播放一面图片
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
        mPlayvideo = (PreViewGSYVideoPlayer) findViewById(R.id.pregsyv);
        msurfaceView = findViewById(R.id.msv);
        mPlayvideo.startAfterPrepared();

        mEditText = (EditText) findViewById(R.id.edit_text);
        mSend = (Button) findViewById(R.id.send);
        mSend.setOnClickListener(this);
        mOperationLayout = (LinearLayout) findViewById(R.id.operation_layout);
        mTanTitle = (TextView) findViewById(R.id.tan_title);
        startVideo();

        //动态生成弹幕
        startBarrage();

    }

    //生成弹幕
    private void startBarrage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    mText = new mText();
                    mText.setText(strings[random.nextInt(strings.length)]);
                    mText.setSpeed(3);
                    mText.setColor(colors[random.nextInt(colors.length)]);
                    mText.setSize(50);
                    msurfaceView.add(mText);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //开始播放视频
    private void startVideo() {
        backupRendType = GSYVideoType.getRenderType();
        //设置为Surface播放模式，注意此设置是全局的
        GSYVideoType.setRenderType(GSYVideoType.SUFRACE);

    }

    //监听事件
    //发送一条弹幕
    @Override
    public void onClick(View v) {
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = mEditText.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    String string = mEditText.getText().toString();
                    mText.setText(string);
                    mEditText.getText().clear();
                }
            }
        });
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
}