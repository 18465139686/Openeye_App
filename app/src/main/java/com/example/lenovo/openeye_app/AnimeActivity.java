package com.example.lenovo.openeye_app;

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeActivity extends AppCompatActivity {

    @BindView(R.id.anim_bg)
    ImageView mAnimBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏模式
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_anime);
        ButterKnife.bind(this);
        initBackground();
        startAnimation();    //开启动画效果

    }

    private void startAnimation() {
        final View viewById = findViewById(R.id.anim_bg);
        ValueAnimator animator=ValueAnimator.ofObject(new FloatEvaluator(),1.0f,1.2f);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value= (Float) animation.getAnimatedValue();
                if (value!=1.2f){
                    viewById.setScaleX(value);
                    viewById.setScaleY(value);
                }else{
                    gotoActivity();
                }
            }

            private void gotoActivity() {
                Intent intent=new Intent(AnimeActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0,android.R.anim.fade_out);
                finish();

            }
        });
        animator.start();
    }

    private void initBackground() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_WEEK) - 1;
        switch (day) {
            case 0:
                mAnimBg.setBackgroundResource(R.drawable.wallpaper_12);
                break;
            case 1:
                mAnimBg.setBackgroundResource(R.drawable.wallpaper_7);
                break;
            case 2:
                mAnimBg.setBackgroundResource(R.drawable.wallpaper_10);
                break;
            case 3:
                mAnimBg.setBackgroundResource(R.drawable.wallpaper_8);
                break;
            case 4:
                mAnimBg.setBackgroundResource(R.drawable.wallpaper_11);
                break;
            case 5:
                mAnimBg.setBackgroundResource(R.drawable.wallpaper_9);
                break;
            case 6:
                mAnimBg.setBackgroundResource(R.drawable.wallpaper_6);
                break;
            default:
                mAnimBg.setBackgroundResource(R.drawable.wallpaper_6);

        }
    }


}
