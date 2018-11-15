package com.cyzn.yzj.snapshot.view.ui;

import android.animation.Animator;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.cyzn.yzj.snapshot.R;
import com.cyzn.yzj.snapshot.base.adapter.TestAdapter;
import com.cyzn.yzj.snapshot.base.listener.OnItemClickListener;
import com.cyzn.yzj.snapshot.base.mvp.BaseActivity;
import com.cyzn.yzj.snapshot.model.bean.TestBean;
import com.cyzn.yzj.snapshot.model.login.LoginApi;
import com.cyzn.yzj.snapshot.presenter.main.MainContact;
import com.cyzn.yzj.snapshot.presenter.main.MainPresenter;
import com.cyzn.yzj.snapshot.util.MyApplication.MyApplication;
import com.cyzn.yzj.snapshot.util.camera.CameraButton;
import com.cyzn.yzj.snapshot.util.other.ScreenSizeUtils;
import com.cyzn.yzj.snapshot.util.other.ToastUtil;
import com.cyzn.yzj.snapshot.view.Application;
import com.github.rubensousa.floatingtoolbar.FloatingToolbar;
import com.github.rubensousa.floatingtoolbar.FloatingToolbarMenuBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author YZJ
 * @description 主页
 * @date 2018/10/19
 */
public class MainActivity extends BaseActivity<MainContact.Presenter> implements MainContact.View, NavigationView.OnNavigationItemSelectedListener {
    private static final Integer FACE = 1;
    private static final Integer CAR = 2;

    private long mExitTime = 0;

    //FloatingToolbar mFloatingToolbar;
    //FloatingActionButton fab;

    private ImageButton button;
    private RelativeLayout rlContent;

    ImageView takePhoto;
    DrawerLayout drawer;

    private List<TestBean.StoriesBean> list = new ArrayList<>();//数据
    private TestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.getInstance().addActivity(this);
        //takePhoto = findViewById(R.id.btn_takePhoto);
        //初始化view
        initView();
        //初始化悬浮按钮点击事件
        buttonClick();
        //初始化界面加载数据
        initData();
        //添加列表item点击事件的监听
        setItemClickListener();
    }

    @Override
    public MainContact.Presenter initPresenter() {
        return new MainPresenter(this);
    }

    //初始化test
    @Override
    public void initData() {
        presenter.getData();
    }

    //初始化view
    @Override
    public void initView() {
        //标题栏右侧Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //左侧菜单栏
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //悬浮按钮选项
        //mFloatingToolbar = findViewById(R.id.floatingToolbar);
        //悬浮按钮
        //fab = findViewById(R.id.fab);

//        mFloatingToolbar.setMenu(new FloatingToolbarMenuBuilder(this)
//                .addItem(FACE, R.drawable.ic_face_white_24dp, "拍人脸")
//                .addItem(CAR, R.drawable.ic_motorcycle_white_24dp, "拍车牌")
//                .build());
//        mFloatingToolbar.attachFab(fab);

        /**
         * 初始化RecyclerView
         */
        RecyclerView recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TestAdapter(list);
        recyclerView.setAdapter(adapter);

        /**
         * 初始化button
         */
        button = findViewById(R.id.button_camera);
        //rlContent = findViewById(R.id.rl_content);
        //rlContent.getBackground().mutate().setAlpha(0);
    }

    //button点击事件
    @Override
    public void buttonClick() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        /*mFloatingToolbar.setClickListener(new FloatingToolbar.ItemClickListener() {

            @Override
            public void onItemClick(MenuItem item) {
                if (item.getItemId() == FACE) {
                    Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == CAR) {
                    Toast.makeText(getApplicationContext(), item.getTitle() + " Short", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onItemLongClick(MenuItem item) {
                Toast.makeText(getApplicationContext(), item.getTitle().toString() + " Long", Toast.LENGTH_SHORT).show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFloatingToolbar.show();
            }
        });*/
    }

    //返回键监听
    /*@Override
    public void onBackPressed() {
        //super.onBackPressed():启用系统返回键功能
        drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mFloatingToolbar.isShowing()) {
            mFloatingToolbar.hide();
        }
    }*/

    //标题栏右侧menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //标题栏右侧menu item的click事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //左侧菜单栏item点击事件监听
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (id == R.id.nav_send) {
            // 解决退出时NavigationView未完全关闭就跳转界面的卡顿
            if (drawer.isDrawerOpen(GravityCompat.END)) {
                drawer.closeDrawers();
            }
            Intent intent = new Intent(MainActivity.this, Application.class);
            startActivity(intent);
            MainActivity.this.finish();
        } else if (id == R.id.nav_about) {
            ToastUtil.showShort(this, item.getTitle());
        }

        return true;
    }

    @Override
    public void setData(List<TestBean.StoriesBean> dataList) {
        list.addAll(dataList);
        adapter.notifyDataSetChanged();
    }

    public void setItemClickListener() {
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Log.e("itemClick", String.valueOf(postion));
            }
        });
    }

    /**
     * 第一次点击时提示:再按一次退出
     * 如果2s以内则退出程序
     * 如果2s之外则提示:再按一次退出
     */
    private void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            MyApplication.getInstance().destory();
        }
    }

    /**
     * 判断是否点击了返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Log.e("onKeyDown", "" + keyCode);
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
