package com.cyzn.yzj.snapshot.model.login;

import android.util.Log;
import com.cyzn.yzj.snapshot.base.retrofit.BaseApi;
import com.cyzn.yzj.snapshot.base.retrofit.ExceptionHelper;
import com.cyzn.yzj.snapshot.model.bean.CameraBean;
import com.cyzn.yzj.snapshot.model.bean.TestBean;
import com.cyzn.yzj.snapshot.model.retrofit.API;
import com.cyzn.yzj.snapshot.presenter.main.MainContact;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

/**
 * @author YZJ
 * @description 登录api
 * @date 2018/10/31 0031
 */
public class LoginApi extends BaseApi {

    private static final String TAG = "LoginApi";
    private API api;
    private static LoginApi loginApi = new LoginApi();

    private LoginApi() {
        super(API.ZHIHU_BASE_URL);
        api = super.createApi();
    }

    public static LoginApi getInstance() {
        return loginApi;
    }

    public void getBGData() {
        api.getBGData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CameraBean>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.e(TAG, "onSubscribe()");
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(CameraBean cameraBean) {
                        Log.e(TAG, "onNext():" + cameraBean.toString());
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        Log.e(TAG, "onError()");
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete()");
                    }

                });
    }

}
