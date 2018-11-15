package com.cyzn.yzj.snapshot.presenter.login;

import com.cyzn.yzj.snapshot.base.mvp.BasePresenterImpl;
import com.cyzn.yzj.snapshot.base.retrofit.ExceptionHelper;
import com.cyzn.yzj.snapshot.model.retrofit.API;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

/**
 * @author YZJ
 * @description 登录presenter类
 * @date 2018/10/19
 */
public class LoginPresenter extends BasePresenterImpl<LoginContact.View> implements LoginContact.Presenter {

    public LoginPresenter(LoginContact.View view) {
        super(view);
    }

    //请求登录
    @Override
    public void requestLogin() {
        // do some network request...
        /*API.getInstance().test()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        // 添加disposable为了在退出界面时解绑，防止Rx造成内存泄漏
                        addDisposable(disposable);
                        // 显示加载条
                    }
                })
                .map(new Function<User, List<User.Info>>() {
                    @Override
                    public List<User.Info> apply(@NonNull User user) throws Exception {
                        return user.getInfos();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User.Info>>() {
                    @Override
                    public void accept(@NonNull List<User.Info> infos) throws Exception {
                        // 取消加载条
                        // 返回数据给view
                        view.setData(infos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        // 取消加载条
                        ExceptionHelper.handleException(throwable);
                    }
                });*/
    }

}
