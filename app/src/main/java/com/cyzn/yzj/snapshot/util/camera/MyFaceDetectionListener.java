package com.cyzn.yzj.snapshot.util.camera;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;

/**
 * @author YZJ
 * @description 人脸检测监听类
 * @date 2018/11/12 0012
 */
public class MyFaceDetectionListener implements Camera.FaceDetectionListener {
    private Context context;
    private Listener listener;

    public MyFaceDetectionListener(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        listener.onFaceDetection(faces);
    }

    interface Listener {
        void onFaceDetection(Camera.Face[] faces);
    }

}
