package com.cyzn.yzj.snapshot.util.camera;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import com.cyzn.yzj.snapshot.R;

public class FocusImageView extends AppCompatImageView {
    private final static String TAG = "FocusImageView";
    private static final int NO_ID = -1;
    private int mFocusImg = NO_ID;
    private int mFocusSucceedImg = NO_ID;
    private int mFocusFailedImg = NO_ID;
    private Animation mAnimation;
    private Handler mHandler;

    public FocusImageView(Context context) {
        this(context, null);
    }

    public FocusImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.focusview_show);
        setVisibility(View.GONE);
        mHandler = new Handler();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FocusImageView);
        mFocusImg = a.getResourceId(R.styleable.FocusImageView_focus_focusing_id, NO_ID);
        mFocusSucceedImg = a.getResourceId(R.styleable.FocusImageView_focus_success_id, NO_ID);
        mFocusFailedImg = a.getResourceId(R.styleable.FocusImageView_focus_fail_id, NO_ID);
        a.recycle();
        if (mFocusImg == NO_ID || mFocusSucceedImg == NO_ID || mFocusFailedImg == NO_ID)
            throw new RuntimeException("Animation is null");
    }

    public void startFocus(Point point) {
        Log.e("startFocus", "startFocus");
        if (mFocusImg == NO_ID || mFocusSucceedImg == NO_ID || mFocusFailedImg == NO_ID)
            throw new RuntimeException("focus image is null");
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
        params.topMargin = point.y - getHeight() / 2;
        params.leftMargin = point.x - getWidth() / 2;
        setLayoutParams(params);
        setVisibility(View.VISIBLE);
        setImageResource(mFocusImg);
        mAnimation.setRepeatMode(Animation.INFINITE);
        startAnimation(mAnimation);
        Log.e("startFocus", "endFocus");
    }

    public void onFocusSuccess() {
        setVisibility(View.VISIBLE);
        mAnimation.cancel();
        setImageResource(mFocusSucceedImg);
        mHandler.removeCallbacks(null, null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setVisibility(View.GONE);
                Log.e("onFocusSuccess", "onFocusSuccess()");
            }
        }, 1000);

    }

    public void onFocusFailed() {
        setVisibility(View.VISIBLE);
        mAnimation.cancel();
        setImageResource(mFocusFailedImg);
        mHandler.removeCallbacks(null, null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setVisibility(View.GONE);
                Log.e("onFocusFailed", "onFocusFailed()");
            }
        }, 1000);
    }

    public void setFocusImg(int focus) {
        this.mFocusImg = focus;
    }


    public void setFocusSucceedImg(int focusSucceed) {
        this.mFocusSucceedImg = focusSucceed;
    }
}
