package com.cyzn.yzj.snapshot.view.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyzn.yzj.snapshot.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author YZJ
 * @description 相机预览界面
 * @date 2018/10/19
 */
public class PreviewActivity extends AppCompatActivity {
    @BindView(R.id.prePhoto)
    ImageView imageView;
    @BindView(R.id.cancel)
    TextView mCancel;
    @BindView(R.id.confirm)
    TextView mConfirm;

    private byte[] data;//人脸数据

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_photo);
        ButterKnife.bind(this);

        data = getIntent().getByteArrayExtra("data");
        imageView.setImageBitmap(byteToBitmap(data));
    }

    @OnClick(R.id.cancel)
    public void doCancel() {
        Intent intent = new Intent(PreviewActivity.this, CameraActivity.class);
        startActivityForResult(intent, 1);
        PreviewActivity.this.finish();
    }

    @OnClick(R.id.confirm)
    public void doConfirm() {
        Intent intent = new Intent(PreviewActivity.this, ResultActivity.class);
        intent.putExtra("data", data);
        startActivityForResult(intent, 2);
        PreviewActivity.this.finish();
    }

    public Bitmap byteToBitmap(byte[] bytes){
        //将byte[]转为图片
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Matrix matrix = new Matrix();
        matrix.setRotate(90);
        //将图片旋转90°
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap;
    }
}
