package com.cyzn.yzj.snapshot.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.cyzn.yzj.snapshot.R;
import com.cyzn.yzj.snapshot.base.mvp.BaseActivity;
import com.cyzn.yzj.snapshot.model.login.LoginApi;
import com.cyzn.yzj.snapshot.presenter.login.LoginContact;
import com.cyzn.yzj.snapshot.presenter.login.LoginPresenter;
import com.cyzn.yzj.snapshot.util.network.NetWorkUtil;
import com.cyzn.yzj.snapshot.util.other.ToastUtil;
import com.cyzn.yzj.snapshot.view.ui.CameraActivity;
import com.cyzn.yzj.snapshot.view.ui.MainActivity;
import com.jakewharton.rxbinding.view.RxView;
import rx.functions.Action1;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author YZJ
 * @description 登录界面
 * @date 2018/10/19
 */
public class Application extends BaseActivity<LoginContact.Presenter> implements LoginContact.View {
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.pwd)
    EditText pwd;
    @BindView(R.id.forgetPwd)
    TextView forgetPwd;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.login)
    Button mLogin;

    private boolean isCommitLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.application);
        ButterKnife.bind(this);

        //按钮防抖处理
        doClick();
        //注册网络接收者
        NetWorkUtil.registerNetConnChangedReceiver(this);
        //监听网络状态
        checkNetStatus();
    }

    @Override
    public LoginContact.Presenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册网络接收者
        NetWorkUtil.unregisterNetConnChangedReceiver(this);
    }

    public void doClick() {
        RxView.clicks(mLogin)
                .throttleFirst(2, TimeUnit.SECONDS)   //两秒钟之内只取一个点击事件，防抖操作
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        //presenter.requestLogin();
                        if (isCommitLogin) {
                            //获取后台数据
                            //LoginApi.getInstance().getBGData();
                            Toast.makeText(getApplicationContext(), "login: " + isCommitLogin, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Application.this, CameraActivity.class);
                            startActivity(intent);
                            Application.this.finish();
                        }
                    }
                });
    }

    //获取登录用户信息
//    @Override
//    public boolean setData(List<User.Info> infos) {
//        //判断登陆请求返回的数据
//        isCommitLogin = true;
//        return isCommitLogin;
//    }

    /**
     * 检查网络状态
     */
    public void checkNetStatus() {
        /**
         * 网络状态监听
         * NO_NETWORK,WIFI,MOBILE,MOBILE_2G,MOBILE_3G,MOBILE_4G,MOBILE_UNKNOWN,OTHER,NO_CONNECTED
         */
        NetWorkUtil.addNetConnChangedListener(new NetWorkUtil.NetConnChangedListener() {
            @Override
            public void onNetConnChanged(NetWorkUtil.ConnectStatus connectStatus) {
                if (connectStatus.equals(NetWorkUtil.ConnectStatus.NO_NETWORK)) {
                    ToastUtil.showLong(getApplicationContext(), NetWorkUtil.ConnectTips.NO_NETWORK);
                } else if (connectStatus.equals(NetWorkUtil.ConnectStatus.NO_CONNECTED)) {
                    ToastUtil.showLong(getApplicationContext(), NetWorkUtil.ConnectTips.NO_CONNECTED);
                } else if (connectStatus.equals(NetWorkUtil.ConnectStatus.WIFI)) {
                    ToastUtil.showLong(getApplicationContext(), NetWorkUtil.ConnectTips.WIFI);
                } else if (connectStatus.equals(NetWorkUtil.ConnectStatus.MOBILE)) {
                    ToastUtil.showLong(getApplicationContext(), NetWorkUtil.ConnectTips.MOBILE);
                } else if (connectStatus.equals(NetWorkUtil.ConnectStatus.MOBILE_2G)) {
                    ToastUtil.showLong(getApplicationContext(), NetWorkUtil.ConnectTips.MOBILE_2G);
                } else if (connectStatus.equals(NetWorkUtil.ConnectStatus.MOBILE_3G)) {
                    ToastUtil.showLong(getApplicationContext(), NetWorkUtil.ConnectTips.MOBILE_3G);
                } else if (connectStatus.equals(NetWorkUtil.ConnectStatus.MOBILE_4G)) {
                    ToastUtil.showLong(getApplicationContext(), NetWorkUtil.ConnectTips.MOBILE_4G);
                } else if (connectStatus.equals(NetWorkUtil.ConnectStatus.MOBILE_UNKNOWN)) {
                    ToastUtil.showLong(getApplicationContext(), NetWorkUtil.ConnectTips.MOBILE_UNKNOWN);
                } else if (connectStatus.equals(NetWorkUtil.ConnectStatus.OTHER)) {
                    ToastUtil.showLong(getApplicationContext(), NetWorkUtil.ConnectTips.OTHER);
                }
            }
        });
    }
}
