package com.cyzn.yzj.snapshot.base.mvp;

import io.reactivex.disposables.Disposable;

/**
 * @author YZJ
 * @description presenter基类接口类
 * @date 2018/10/19
 */
public interface BasePresenter {
    //默认初始化
    void init();

    //Activity关闭把view对象置为空
    void detach();

    //将网络请求的每一个disposable添加进入CompositeDisposable，再退出时候一并注销
    void addDisposable(Disposable subscription);

    //注销所有请求
    void unDisposable();

}
