package com.cyzn.yzj.snapshot.base.retrofit;

import android.util.Log;
import com.cyzn.yzj.snapshot.model.retrofit.API;
import com.google.gson.GsonBuilder;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author YZJ
 * @description Api基类实现类
 * @date 2018/10/19
 */
public class BaseApi {
    private volatile static Retrofit retrofit = null;
    private API api;
    protected Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
    protected OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();

    public BaseApi(String baseUrl) {
        retrofitBuilder.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()
                ))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpBuilder.addInterceptor(getLoggerInterceptor()).build())
                .baseUrl(baseUrl);
    }

    /**
     * 构建retroft
     *
     * @return Retrofit对象
     */
    public Retrofit createRetrofit() {
        if (retrofit == null) {
            //锁定代码块
            synchronized (BaseApi.class) {
                if (retrofit == null) {
                    retrofit = retrofitBuilder.build(); //创建retrofit对象
                }
            }
        }
        return retrofit;
    }

    public API createApi() {
        api = createRetrofit().create(API.class);
        return api;
    }


    public OkHttpClient.Builder setInterceptor(Interceptor interceptor) {
        return httpBuilder.addInterceptor(interceptor);
    }

    public Retrofit.Builder setConverterFactory(Converter.Factory factory) {
        return retrofitBuilder.addConverterFactory(factory);
    }

    /**
     * 日志拦截器
     * 将你访问的接口信息
     *
     * @return 拦截器
     */
    public HttpLoggingInterceptor getLoggerInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.HEADERS;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("ApiUrl", "--->" + message);
            }
        });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }

}
