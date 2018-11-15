package com.cyzn.yzj.snapshot.presenter.login;

import com.cyzn.yzj.snapshot.base.mvp.BasePresenter;
import com.cyzn.yzj.snapshot.base.mvp.BaseView;

import java.util.List;

/**
 * @author YZJ
 * @description 登录接口类
 * @date 2018/10/19
 */
public interface LoginContact {

    interface View extends BaseView {
        //获取用户信息
        //boolean setData(List<User.Info> infos);
    }

    interface Presenter extends BasePresenter {
        //请求登录
        void requestLogin();
    }
}
