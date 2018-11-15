package com.cyzn.yzj.snapshot.util.camera;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.cyzn.yzj.snapshot.R;

public class CameraButton extends AppCompatButton {

    private int width;
    private int heigh;

    private GradientDrawable backDrawable;

    public CameraButton(Context context) {
        super(context);
        init();
    }

    public CameraButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CameraButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        backDrawable = new GradientDrawable();
        int colorDrawable = getResources().getColor(R.color.cutePink);
        backDrawable.setColor(colorDrawable);
        backDrawable.setCornerRadius(120);
        setBackground(backDrawable);
        //setText("拍照");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heighMode = MeasureSpec.getMode(heightMeasureSpec);
        int heighSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }
        if (heighMode == MeasureSpec.EXACTLY) {
            heigh = heighSize;
        }
    }

    public void startAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(width, heigh);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                int leftOffset = (width - value) / 2;
                int rightOffset = width - leftOffset;
                backDrawable.setBounds(leftOffset, 0, rightOffset, heigh);
            }
        });
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(backDrawable, "cornerRadius", 120, heigh / 2);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(valueAnimator, objectAnimator);
        animatorSet.start();
    }

    public void gotoCamera() {
        setVisibility(GONE);
    }

    public void regainBackground() {
        setVisibility(VISIBLE);
        init();
    }

}
