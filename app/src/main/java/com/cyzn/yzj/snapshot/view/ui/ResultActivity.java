package com.cyzn.yzj.snapshot.view.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.cyzn.yzj.snapshot.R;

/**
 * @author YZJ
 * @description 拍照展示界面
 * @date 2018/10/19
 */
public class ResultActivity extends AppCompatActivity {
    private ImageView imageView;
    //人脸数据
    private byte[] data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        imageView = findViewById(R.id.img_pic);

        data = getIntent().getByteArrayExtra("data");
        imageView.setImageBitmap(byteToBitmap(data));
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

    //返回键监听
    @Override
    public void onBackPressed() {
        //super.onBackPressed():启用系统返回键功能
        Intent intent = new Intent(ResultActivity.this, CameraActivity.class);
        startActivity(intent);
        ResultActivity.this.finish();
    }
}
