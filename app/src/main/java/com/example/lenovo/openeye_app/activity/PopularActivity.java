package com.example.lenovo.openeye_app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.openeye_app.R;
import com.example.lenovo.openeye_app.RxUtil.ToastUtil;
import com.example.lenovo.openeye_app.activity.GSYV.PopuVideoActivity;
import com.example.lenovo.openeye_app.base.BaseApp;
import com.example.lenovo.openeye_app.customdialog.AlertDialog;
import com.example.lenovo.openeye_app.customdialog.DownloadDB;
import com.example.lenovo.openeye_app.customdialog.DownloadDetails;
import com.example.lenovo.openeye_app.customdialog.HistoryDB;
import com.example.lenovo.openeye_app.customdialog.HistoryDetails;
import com.example.lenovo.openeye_app.fragment.popular.PopuReyclerAdapter;
import com.example.lenovo.openeye_app.utils.NetConnectedUtils;
import com.example.lenovo.openeye_app.utils.VideoDownloadUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopularActivity extends AppCompatActivity {


    @BindView(R.id.find_back)
    ImageButton finhdback;
    @BindView(R.id.video_toolbar_iv_right)
    ImageButton videoToolbarIvRight;
    @BindView(R.id.video_toolbar)
    Toolbar videoToolbar;
    @BindView(R.id.video_detail_iv)
    SimpleDraweeView videoDetailIv;
    @BindView(R.id.video_paly)
    ImageView videopaly;
    @BindView(R.id.video_detail_ivmo)
    SimpleDraweeView videodetailivmo;
    @BindView(R.id.video_detail_title)
    TextView videodetailtitle;
    @BindView(R.id.video_detail_time)
    TextView videodetailtime;
    @BindView(R.id.video_detail_desc)
    TextView videodetaildesc;
    @BindView(R.id.video_detail_iv_fav)
    ImageView videodetailivfav;
    @BindView(R.id.video_detail_tv_fav)
    TextView videodetailtvfav;
    @BindView(R.id.video_detail_iv_share)
    ImageView videodetailivshare;
    @BindView(R.id.video_detail_tv_share)
    TextView videodetailtvshare;
    @BindView(R.id.video_detail_ll_share)
    LinearLayout videodetailllshare;
    @BindView(R.id.video_detail_iv_reply)
    ImageView videodetailivreply;
    @BindView(R.id.video_detail_tv_reply)
    TextView videodetailtvreply;
    @BindView(R.id.video_detail_iv_down)
    ImageView videodetailivdown;
    @BindView(R.id.video_detail_tv_down)
    TextView videodetailtvdown;
    @BindView(R.id.video_detail_ll_down)
    LinearLayout videodetaillldown;

    private HistoryDB hdb;
    private DownloadDB db;

    private String sdpath;//手机内置sdcard存储路径
    private List<DownloadDetails> templist;
    private SharedPreferences sharedPreferences = BaseApp.sharedPreferences;
    private SharedPreferences.Editor editor = BaseApp.editor;
    private String feed;
    private String title;
    private String time;
    private String desc;
    private String blurred;
    private String video;
    private int collect;
    private int share;
    private int reply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);
        ButterKnife.bind(this);
        initData();
        hdb = new HistoryDB();
        db = new DownloadDB();
    }

    private void initData() {
        //获取数据
        //获取图片
        feed = getIntent().getStringExtra("feed");
        title = getIntent().getStringExtra("title");
        //时间
        time = getIntent().getStringExtra("time");
        //视频详情
        desc = getIntent().getStringExtra("desc");
        //模糊图片
        blurred = getIntent().getStringExtra("blurred");
        video = getIntent().getStringExtra("video");
        //收藏量
        collect = getIntent().getIntExtra("collect", 0);
        //分享量
        share = getIntent().getIntExtra("share", 0);
        //回复量
        reply = getIntent().getIntExtra("reply", 0);
        //初始化界面数据
        videoDetailIv.setImageURI(Uri.parse(feed));
        videodetailtitle.setText(title);
        videodetailtime.setText(time);
        videodetaildesc.setText(desc);
        videodetailivmo.setImageURI(Uri.parse(blurred));
        videodetailtvfav.setText(String.valueOf(collect));
        videodetailtvshare.setText(String.valueOf(share));
        videodetailtvshare.setText(String.valueOf(reply));

    }

    @OnClick({R.id.video_toolbar_iv_right, R.id.find_back})
    public void onClick() {
        finish();
    }

    @OnClick({R.id.video_paly, R.id.video_detail_iv_fav, R.id.video_detail_ll_share, R.id.video_detail_iv_reply, R.id.video_detail_ll_down})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.video_paly://播放视频
                    Intent intent = new Intent(this, PopuVideoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("video", video);
                    bundle.putString("title", title);
                    bundle.putString("img",blurred);
                    bundle.putString("feed",feed);
                    intent.putExtras(bundle);
                    startActivity(intent);

                break;
            case R.id.video_detail_iv_fav://收藏
                break;
            case R.id.video_detail_ll_share://分享

                break;
            case R.id.video_detail_iv_reply://评论
                break;
            case R.id.video_detail_ll_down://下载
                downloadVideo();
                break;
        }
    }

    private void downloadVideo() {
        templist = db.loadDownByTitle(title);
        if (templist != null && templist.size() != 0 && "已缓存".equals(templist.get(0).getState())) {
            ToastUtil.showShort("该视频已缓存");
            return;
        }
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            ToastUtil.showShort("已加入缓存队列");
            setOutdownload();
        }
    }

    private void setOutdownload() {
        sdpath = Environment.getExternalStorageDirectory().getPath() + File.separator + "eyepetizers";//I自定义视频保存路径
        String destFileName = title + ".mp4";
        VideoDownloadUtil.getInstance().download(video, sdpath, destFileName, new VideoDownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                String path = file + "";
                DownloadDetails downloadDetails = new DownloadDetails(feed, title, time, path, "已缓存");
                if (templist == null) {
                    db.saveOrUpdate(downloadDetails);
                } else if (templist.size() == 0) {
                    db.saveOrUpdate(downloadDetails);
                } else if ("未缓存".equals(templist.get(0).getState())) {
                    db.delete(templist.get(0));
                    db.saveOrUpdate(downloadDetails);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showShort("下载成功");
                    }
                });

            }

            @Override
            public void onDownloading(int progress) {

            }

            @Override
            public void onDownloadFailed(Exception e) {
                ToastUtil.showShort("下载失败");
            }
        });
    }
    public String getDate(){
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        return month+"月"+day+"日";
    }

}
