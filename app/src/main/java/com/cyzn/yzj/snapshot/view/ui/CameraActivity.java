package com.cyzn.yzj.snapshot.view.ui;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.cyzn.yzj.snapshot.R;
import com.cyzn.yzj.snapshot.util.camera.CameraButton;
import com.cyzn.yzj.snapshot.util.camera.CameraContainer;
import com.cyzn.yzj.snapshot.util.camera.CameraSurfaceView;
import com.cyzn.yzj.snapshot.util.camera.FaceView;

/**
 * @author YZJ
 * @description 相机界面
 * @date 2018/10/19
 */
@SuppressLint("MissingPermission")
public class CameraActivity extends AppCompatActivity implements CameraSurfaceView.Listener {

    private static final int REQUEST_PREVIEW_CODE = 1001;
    private Camera mCamera;

    private CameraContainer mCameraContainer;
    private CameraSurfaceView mCameraSurfaceView;

    @BindView(R.id.btn_takephoto)
    ImageView btn_takephoto;
    @BindView(R.id.switch_camera_iv_potrait)
    ImageView btn_switch_camera;
    @BindView(R.id.switch_flash_iv_potrait)
    ImageView btn_switch_flash;
    @BindView(R.id.close_record_iv_potrait)
    ImageView btn_camera_close;
    FaceView faceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        mCameraContainer = findViewById(R.id.container);
        mCameraSurfaceView = mCameraContainer.getmCameraSurfaceView();

        //调用SurfaceView中的listener
        createCameraSurfaceView(mCameraSurfaceView);
        //初始化拍照button
        initTakePhoneButton();

    }

    private void initTakePhoneButton() {
        btn_takephoto.setClickable(false);
        btn_takephoto.setOnClickListener(null);
        btn_takephoto.setImageResource(R.drawable.ic_capture_unsee);
    }

    @OnClick({R.id.switch_camera_iv_potrait, R.id.switch_flash_iv_potrait, R.id.close_record_iv_potrait})
    public void onButtonClick(View v) {
        switch (v.getId()) {
            // 切换摄像头
            case R.id.switch_camera_iv_potrait:
                mCameraSurfaceView.switchCamera();
                break;

            // 切换闪光灯
            case R.id.switch_flash_iv_potrait:
                mCameraSurfaceView.switchFlash();
                break;

            // 关闭相机
            case R.id.close_record_iv_potrait:
                Intent intent = new Intent(CameraActivity.this, MainActivity.class);
                startActivity(intent);
                CameraActivity.this.finish();
                break;
        }
    }

    public void takePicture() {
        mCamera.takePicture(null, null, mPictureCallback);
    }

    public final Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {
            Log.e("onPictureTaken", String.valueOf(data.length));
            Intent intent = new Intent(CameraActivity.this, PreviewActivity.class);
            intent.putExtra("data", data);
            startActivityForResult(intent, REQUEST_PREVIEW_CODE);
            CameraActivity.this.finish();
        }
    };

    //返回键监听
    @Override
    public void onBackPressed() {
        //super.onBackPressed():启用系统返回键功能
        Intent intent = new Intent(CameraActivity.this, MainActivity.class);
        startActivity(intent);
        CameraActivity.this.finish();
    }

    //人脸检测最终返回接口
    @Override
    public void onFaceDetected(Camera.Face[] faces) {
        if (faces.length > 0) {
            Log.e("faceDetection", "face detected: " + faces.length + ", face 1 location X: " + faces[0].rect.centerX() + ", Y: " + faces[0].rect.centerY());
            // 拍照
            btn_takephoto.setClickable(true);
            btn_takephoto.setImageResource(R.drawable.shot_img_selector);
            btn_takephoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCamera = mCameraSurfaceView.getmCamera();
                    if (mCamera != null) {
                        takePicture();
                    }
                }
            });
        } else {
            initTakePhoneButton();
        }
    }

    @Override
    public void createCameraSurfaceView(CameraSurfaceView view) {
        view.initListener(this);
    }

}
