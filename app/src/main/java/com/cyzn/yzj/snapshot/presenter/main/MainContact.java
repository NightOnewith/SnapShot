package com.cyzn.yzj.snapshot.presenter.main;

import com.cyzn.yzj.snapshot.base.mvp.BasePresenter;
import com.cyzn.yzj.snapshot.base.mvp.BaseView;
import com.cyzn.yzj.snapshot.model.bean.TestBean;

import java.util.List;

/**
 * @author YZJ
 * @description 主界面接口类
 * @date 2018/10/19
 */
public interface MainContact {

    interface View extends BaseView {
        void initData();

        void initView();

        void buttonClick();

        /**
         * 设置数据test
         *
         * @param dataList
         */
        void setData(List<TestBean.StoriesBean> dataList);
    }

    interface Presenter extends BasePresenter {
        /**
         * 获取数据test
         */
        void getData();
    }
}
