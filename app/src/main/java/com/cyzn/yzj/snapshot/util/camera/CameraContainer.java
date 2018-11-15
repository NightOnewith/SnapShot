package com.cyzn.yzj.snapshot.util.camera;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cyzn.yzj.snapshot.R;

public class CameraContainer extends RelativeLayout {
    private Context mContext;
    private Camera mCamera;

    private FocusImageView mFocusImageView;
    private CameraSurfaceView mCameraSurfaceView;
    private FaceView mFaceView;

    private int touchMode = 1;
    private final int Single = 1;
    private final int TWO = 2;
    private float startTouchDistance = 0;
    private float moveTouchDistance = 0;
    private int scale = 0;
    private int currenZoom = 0;

    public CameraContainer(Context context) {
        this(context, null);
    }

    public CameraContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.cameracontainer, this);
        mCameraSurfaceView = findViewById(R.id.surfaceView);
        mFocusImageView = findViewById(R.id.focusImageView);
        mFaceView = findViewById(R.id.face_view);

        mCameraSurfaceView.setFaceView(mFaceView);

        mCamera = mCameraSurfaceView.getmCamera();
        setOnTouchListener(new TouchListener());
    }

    public FocusImageView getmFocusImageView() {
        return mFocusImageView;
    }

    public void setmFocusImageView(FocusImageView mFocusImageView) {
        this.mFocusImageView = mFocusImageView;
    }

    public CameraSurfaceView getmCameraSurfaceView() {
        return mCameraSurfaceView;
    }

    public void setmCameraSurfaceView(CameraSurfaceView mCameraSurfaceView) {
        this.mCameraSurfaceView = mCameraSurfaceView;
    }

    public FaceView getmFaceView() {
        return mFaceView;
    }

    public void setmFaceView(FaceView mFaceView) {
        this.mFaceView = mFaceView;
    }

    private final class TouchListener implements OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    touchMode = Single;
                    Log.i("onTouch", "ACTION_DOWN: " + touchMode);
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    touchMode = TWO;
                    Log.i("touch", "ACTION_POINTER_DOWN: " + touchMode);
                    startTouchDistance = mCameraSurfaceView.distance(motionEvent);
                    break;

                case MotionEvent.ACTION_MOVE:
                    // 两根手指缩放
                    if (touchMode == TWO) {
                        Log.i("touch", "ACTION_MOVE: " + motionEvent.getPointerCount());
                        if (motionEvent.getPointerCount() < 2) return true;
                        moveTouchDistance = mCameraSurfaceView.distance(motionEvent);
                        scale = (int) ((moveTouchDistance - startTouchDistance) / 5f);
                        Log.i("touch", "onTouch: " + motionEvent.getPointerCount());
                        if (scale >= 1 || scale <= -1) {
                            currenZoom = mCameraSurfaceView.getParameters().getZoom() + scale;
                            currenZoom = currenZoom > mCameraSurfaceView.getParameters().getMaxZoom() ? mCameraSurfaceView.getParameters().getMaxZoom() : currenZoom;
                            //设置zoom
                            mCameraSurfaceView.setZoom(currenZoom);
                            startTouchDistance = moveTouchDistance;
                        }
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    // 手指抬起
                    if(mCameraSurfaceView.getCameraId() == Camera.CameraInfo.CAMERA_FACING_BACK) {
                        Log.e("touch", "ACTION_UP: " + touchMode);
                        Point point = new Point((int) motionEvent.getX(), (int) motionEvent.getY());
                        Log.e("point", String.valueOf(point.x) + ", " + String.valueOf(point.y));
                        mCameraSurfaceView.onFocus(point, new Camera.AutoFocusCallback() {
                            @Override
                            public void onAutoFocus(boolean b, Camera camera) {
                                if (b) {  //手动对焦成功
                                    mFocusImageView.onFocusSuccess();
                                } else {
                                    mFocusImageView.onFocusFailed();
                                }
                            }
                        });
                        mFocusImageView.startFocus(point);//对焦动画
                    }
                    break;

            }
            return true;
        }

    }

}