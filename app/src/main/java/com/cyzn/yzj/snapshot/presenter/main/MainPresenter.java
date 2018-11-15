package com.cyzn.yzj.snapshot.presenter.main;

import com.cyzn.yzj.snapshot.base.mvp.BasePresenterImpl;
import com.cyzn.yzj.snapshot.base.retrofit.ExceptionHelper;
import com.cyzn.yzj.snapshot.model.bean.TestBean;
import com.cyzn.yzj.snapshot.model.login.LoginApi;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

/**
 * @author YZJ
 * @description 主界面presenter类
 * @date 2018/10/19
 */
public class MainPresenter extends BasePresenterImpl<MainContact.View> implements MainContact.Presenter {

    public MainPresenter(MainContact.View view) {
        super(view);
    }

    @Override
    public void getData() {
        LoginApi.getInstance().createApi().test()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        addDisposable(disposable);
                        //view.showLoadingDialog("");
                    }
                })
                .map(new Function<TestBean, List<TestBean.StoriesBean>>() {
                    @Override
                    public List<TestBean.StoriesBean> apply(@NonNull TestBean testBean) throws Exception {
                        return testBean.getStories();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TestBean.StoriesBean>>() {
                    @Override
                    public void accept(@NonNull List<TestBean.StoriesBean> storiesBeen) throws Exception {
                        //view.dismissLoadingDialog();
                        view.setData(storiesBeen);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //view.dismissLoadingDialog();
                        ExceptionHelper.handleException(throwable);
                    }
                });
    }
}
