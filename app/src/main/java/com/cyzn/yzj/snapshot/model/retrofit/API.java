package com.cyzn.yzj.snapshot.model.retrofit;

import com.cyzn.yzj.snapshot.model.bean.CameraBean;
import com.cyzn.yzj.snapshot.model.bean.TestBean;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author YZJ
 * @description 基础网络访问
 * @date 2018/10/19 0019
 */
public interface API {

    String BASE_URL = "http://192.168.50.131:8081";
    String ZHIHU_BASE_URL = "https://news-at.zhihu.com/api/4/";
    /**
     * 网络请求
     */
    @GET("camera/list?currntPage=1&pageSize=20")
    Flowable<CameraBean> getBGData();

    @GET("news/latest")
    Observable<TestBean> test();
    /**
     * 网络提交
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("api/data/Add")
    Call<CameraBean> postCameraBean(@Body RequestBody cameraBean);//传入的参数为RequestBody
}
