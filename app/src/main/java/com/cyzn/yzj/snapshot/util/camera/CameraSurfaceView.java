package com.cyzn.yzj.snapshot.util.camera;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import butterknife.BindView;
import com.cyzn.yzj.snapshot.R;
import com.cyzn.yzj.snapshot.view.ui.CameraActivity;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback, MyFaceDetectionListener.Listener {
    private Context context;
    private FaceView faceView;

    private Camera mCamera;
    private Camera.Parameters parameters;
    private Listener listener;

    private SurfaceHolder surfaceHolder;
    protected CameraState cameraState;
    private CameraStateListener cameraStateListener;
    private FlashMode flashMode = FlashMode.OFF;
    private boolean mPreviewing = false;
    private int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;//默认后置

    public CameraSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public Camera getmCamera() {
        return mCamera;
    }

    public int getCameraId() {
        return cameraId;
    }

    public Camera.Parameters getParameters() {
        return parameters;
    }

    public void setFaceView(FaceView faceView) {
        this.faceView = faceView;
    }

    private void init(Context context) {
        this.context = context;
        cameraState = CameraState.START;
        if (cameraStateListener != null) {
            cameraStateListener.onCameraStateChange(cameraState);
        }
        surfaceHolder = getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.setKeepScreenOn(true);
        surfaceHolder.setFixedSize(1920, 1080);
        surfaceHolder.addCallback(this);
        post(new Runnable() {
            @Override
            public void run() {
                openCamera();
                startPreview();
            }
        });

    }

    public void initListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e("surfaceCreated", "surfaceCreated");
        this.surfaceHolder = holder;
        openCamera();
        startPreview();
        startFaceDetection();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e("surfaceChanged", "surfaceChanged");
        this.surfaceHolder = holder;
        if (holder.getSurface() == null) {
            return;
        }

        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            Log.e("mCamera", "Error stop camera preview: " + e.getMessage());
        }

        try {
            openCamera();
            startPreview();
            startFaceDetection();
        } catch (Exception e) {
            Log.e("mCamera", "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e("surfaceDestroyed", "surfaceDestroyed");
        this.surfaceHolder = holder;
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                touchMode = Single;
                // 手指按下
                Log.e("touch", "ACTION_DOWN: " + touchMode);
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                touchMode = TWO;
                Log.i("touch", "ACTION_POINTER_DOWN: " + touchMode);
                startTouchDistance = distance(event);
                break;

            case MotionEvent.ACTION_MOVE:
                // 两根手指缩放
                if (touchMode == TWO) {
                    Log.i("touch", "ACTION_MOVE: " + event.getPointerCount());
                    if (event.getPointerCount() < 2) return true;
                    moveTouchDistance = distance(event);
                    scale = (int) ((moveTouchDistance - startTouchDistance) / 5f);
                    Log.i("touch", "onTouch: " + event.getPointerCount());
                    if (scale >= 1 || scale <= -1) {
                        currenZoom = mCamera.getParameters().getZoom() + scale;
                        currenZoom = currenZoom > mCamera.getParameters().getMaxZoom() ? mCamera.getParameters().getMaxZoom() : currenZoom;
                        //设置zoom
                        setZoom(currenZoom);
                        startTouchDistance = moveTouchDistance;
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                // 手指抬起
                Log.e("touch", "ACTION_UP: " + touchMode);
                Point point = new Point((int) event.getX(), (int) event.getY());
                Log.e("point", String.valueOf(point.x) + ", " + String.valueOf(point.y));
                onFocus(point, new Camera.AutoFocusCallback() {
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
                break;
        }
        return true;
    }*/

    //两根手指移动距离
    public float distance(MotionEvent event) {
        try {
            float dx = event.getX(1) - event.getX(0);
            float dy = event.getY(1) - event.getY(0);
            return (float) Math.sqrt(dx * dx + dy * dy);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //设置缩放zoom
    public void setZoom(int zoom) {
        if (zoom < 0) {
            zoom = 0;
        }
        if (zoom > mCamera.getParameters().getMaxZoom()) {
            zoom = mCamera.getParameters().getMaxZoom();
        }
        Log.i("zoom", "setZoom: " + zoom);
        try {
            parameters.setZoom(zoom);
            mCamera.setParameters(parameters);
            startFaceDetection();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    // 打开相机
    public void openCamera() {
        if (null == mCamera) {
            mCamera = Camera.open(cameraId);
        }
    }

    // 相机参数的初始化设置
    public void startPreview() {
        parameters = mCamera.getParameters();
        parameters.setPictureFormat(PixelFormat.JPEG);
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        if (cameraId == 0) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// 1.连续对焦
        }
        mCamera.setParameters(parameters);
        setDispaly(mCamera);
        try {
            mCamera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();
        // 实现自动对焦
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    camera.cancelAutoFocus();// 只有加上了这一句，才会自动对焦
                }
            }
        });
        mCamera.cancelAutoFocus();// 2.如果要实现连续的自动对焦，这一句必须加上
        mPreviewing = true;
    }

    // 开启人脸检测
    public void startFaceDetection() {
        parameters = mCamera.getParameters();
        if (parameters.getMaxNumDetectedFaces() > 0) {
            if (faceView != null) {
                faceView.clearFaces();
                faceView.setVisibility(View.VISIBLE);
            }
            mCamera.setFaceDetectionListener(new MyFaceDetectionListener(context, this));
            mCamera.startFaceDetection();
        }
    }

    // 控制图像的正确显示方向
    private void setDispaly(Camera camera) {
        if (Integer.parseInt(Build.VERSION.SDK) >= 8) {
            setDisplayOrientation(camera, 90);
        } else {
            parameters.setRotation(-90);
        }

    }

    // 实现的图像的正确显示
    private void setDisplayOrientation(Camera camera, int i) {
        Method downPolymorphic;
        try {
            downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[]{int.class});
            if (downPolymorphic != null) {
                downPolymorphic.invoke(camera, new Object[]{i});
            }
        } catch (Exception e) {
            Log.e("Camera_e", "图像出错");
        }
    }

    public enum CameraState {
        START, PREVIEW, STOP, ERROR
    }

    public void setOnCameraStateListener(CameraStateListener listener) {
        this.cameraStateListener = listener;
    }

    public interface CameraStateListener {
        void onCameraStateChange(CameraState paramCameraState);
    }


    /**************************************** 手动聚焦 ****************************************/
    /**
     * @param point 手指点击的坐标
     * @param autoFocusCallback
     * @return
     */
    public void onFocus(Point point, Camera.AutoFocusCallback autoFocusCallback) {
        if (parameters.getMaxNumFocusAreas() <= 0) {
            mCamera.autoFocus(autoFocusCallback);
            return;
        }
        mCamera.cancelAutoFocus();
        List<Camera.Area> areas = new ArrayList<>();
        List<Camera.Area> areasMetrix = new ArrayList<>();
        Camera.Size previewSize = parameters.getPreviewSize();
        Rect focusRect = calculateTapArea(point.x, point.y, 1.0f, previewSize);
        Rect metrixRect = calculateTapArea(point.x, point.y, 1.5f, previewSize);

        areas.add(new Camera.Area(focusRect, 1000));
        areasMetrix.add(new Camera.Area(metrixRect, 1000));
        parameters.setMeteringAreas(areasMetrix);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        parameters.setFocusAreas(areas);
        try {
            mCamera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        focus(autoFocusCallback);
    }

    private Rect calculateTapArea(float x, float y, float coefficient, Camera.Size previewSize) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int screenHeight = display.getHeight();
        int screenWidth = display.getWidth();
        float focusAreaSize = 200;
        int areaSize = Float.valueOf(focusAreaSize * coefficient).intValue();
        int centerY = 0;
        int centerX = 0;
        centerY = (int) (-x / screenWidth * 2000 + 1000);
        centerX = (int) (y / screenHeight * 2000 - 1000);
        int left = clamp(centerX - areaSize / 2, -1000, 1000);
        int top = clamp(centerY - areaSize / 2, -1000, 1000);
        RectF rectF = new RectF(left, top, left + areaSize, top + areaSize);
        return new Rect(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
    }

    private static int clamp(int x, int min, int max) {
        if (x > max) {
            return max;
        }
        if (x < min) {
            return min;
        }
        return x;
    }

    private boolean focus(Camera.AutoFocusCallback callback) {
        try {
            mCamera.autoFocus(callback);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**************************************** 切换闪光灯 ****************************************/
    public enum FlashMode {
        ON,
        OFF,
    }

    public void switchFlash() {
        flashMode = flashMode == FlashMode.OFF ? FlashMode.ON : FlashMode.OFF;
        switch (flashMode) {
            case OFF:
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                break;
            case ON:
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                break;
        }
        mCamera.setParameters(parameters);
    }

    /**************************************** 切换摄像头 ****************************************/
    public void switchCamera() {
        closeCamera();
        cameraId = cameraId == Camera.CameraInfo.CAMERA_FACING_BACK ? Camera.CameraInfo.CAMERA_FACING_FRONT : Camera.CameraInfo.CAMERA_FACING_BACK;
        openCamera();
        startPreview();
    }

    /**
     * 关闭摄像头
     */
    public void closeCamera() {
        if (mCamera == null) {
            return;
        }
        try {
            if (mCamera != null) {
                stopPreview();
                mCamera.release();
            }
            mCamera = null;
            parameters = null;
            mPreviewing = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止摄像头预览
     */
    public void stopPreview() {
        if (mPreviewing = true) {
            try {
                mCamera.stopPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mPreviewing = false;
        }
    }

    /**************************************** 人脸检测接口 ****************************************/
    @Override
    public void onFaceDetection(Camera.Face[] faces) {
        faceView.setFaces(faces);
        listener.onFaceDetected(faces);
    }

    public interface Listener {
        void onFaceDetected(Camera.Face[] faces);

        void createCameraSurfaceView(CameraSurfaceView view);
    }

}
