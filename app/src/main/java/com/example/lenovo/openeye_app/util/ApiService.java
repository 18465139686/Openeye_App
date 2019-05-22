package com.example.lenovo.openeye_app.util;

import com.example.lenovo.openeye_app.bean.BeanPopu;
import com.example.lenovo.openeye_app.bean.find.BeanFind;
import com.example.lenovo.openeye_app.bean.home.BeanHome;
import com.example.lenovo.openeye_app.fragment.find.fragment.BeanFindTime;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Lenovo on 2019/5/15.
 */

public interface ApiService {

   /* //每日精选
    public static final String DAILY="http://baobab.wandoujia.com/api/v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83";
    //发现更多
    public static final String FIND_MORE="http://baobab.wandoujia.com/api/v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83";
    //热门排行
    public static final String HOT_STRATEGY="http://baobab.wandoujia.com/api/v3/ranklist?num=10&strategy=%s&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83";
    //发现更多详情接口
    public static final String FIND_DETAIL="http://baobab.kaiyanapp.com/api/v4/discovery/category";
    //默认头像地址
    public static final String USER_ICON="https://upload-images.jianshu.io/upload_images/5549640-bc72cbbac2138c94.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240";
    //关键字搜索接口
    //http://baobab.kaiyanapp.com/api/v1/search?num=10&query=小姐姐
    public static final String KEYWORD_SEARCH="http://baobab.kaiyanapp.com/api/v1/search?start=1&num=10&query=";
    //public static final String FIND_DETAIL="http://baobab.wandoujia.com/api/v3/videos?categoryName=%s&strategy=%s&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83";
    //http://baobab.wandoujia.com/api/v3/ranklist?num=10&strategy=monthly&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83
    //热映电影接口
    public static final  String HOT_VIDEO="https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&start=0&count=100&client=&udid=";
    //电影介绍接口,id来自热映电影中对于id;
    public static final String VIDEO = "http://api.douban.com/v2/movie/subject/";
    public static final String VIDEO_ID ="?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&client=&udid=";

    //天气接口
    public static final String WEATHER = "https://free-api.heweather.com/s6/weather/now?location=wuxi&key=746d5b65bae249439419ecb319a8663e";
*/


    //每日精选
    String homeUrl = "http://baobab.wandoujia.com/api/";

    @GET("v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&")
    Observable<BeanHome> gethome(@Query("page") int page);

    //发现更多
    String findUrl = "http://baobab.wandoujia.com/api/";

    @GET("v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    Observable<List<BeanFind>> getfind();

    String popuUrl = "http://baobab.wandoujia.com/api/";

    @GET("v3/ranklist?num=10&strategy=%s&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    Observable<BeanPopu> getpopu();


    String findTimeUrl = "http://baobab.kaiyanapp.com/api/";

    @GET("v4/discovery/category")
    Observable<BeanFindTime> getfindTime();
}
