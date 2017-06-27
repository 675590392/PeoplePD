package com.yingde.gaydcj.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.yingde.gaydcj.R;
import com.yingde.gaydcj.application.PeopleApplication;
import com.yingde.gaydcj.util.MyToolbar;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TResult;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComingSHPeopleAddPhotoActivity extends TakePhotoActivity {

    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.imag_photo_show)
    ImageView imagPhotoShow;
    @BindView(R.id.btn_photo_confirm)
    Button btnPhotoConfirm;

    String filepath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coming_shpeople_add_photo);
        ButterKnife.bind(this);
        //拍照
        takePhoto();
        if (toolbar != null) {
            initTitle();
        }
    }


    /**
     * 拍照
     */
    private void takePhoto() {
        File file = new File(Environment.getExternalStorageDirectory(), PeopleApplication.IMAG_NAME + "/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        final Uri imageUri = Uri.fromFile(file);
        new AlertDialog.Builder(ComingSHPeopleAddPhotoActivity.this)
                .setTitle("小贴士")
                .setItems(new String[]{"用户相册", "拍照"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        configCompress(getTakePhoto());
                        if (which == 0) {
                            getTakePhoto().onPickFromGallery();
                        } else {
                            getTakePhoto().onPickFromCapture(imageUri);
                        }
                    }
                }).setNegativeButton("取消", null)
                .show();
    }

    /**
     * 压缩图片配置
     *
     * @param takePhoto
     */
    private void configCompress(TakePhoto takePhoto) {
        int maxSize = 1 * 1000 * 1000;//200kb
        int width = 400;
        int height = 800;
        CompressConfig config = new CompressConfig.Builder()
                .setMaxSize(maxSize)
                .setMaxPixel(width >= height ? width : height)
                .enableReserveRaw(true)//保留源文件
                .create();

        takePhoto.onEnableCompress(config, false);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        filepath = result.getImage().getCompressPath();
        Bitmap bitmap = BitmapFactory.decodeFile(filepath);
        imagPhotoShow.setImageBitmap(bitmap);
    }


    @OnClick(R.id.btn_photo_confirm)
    public void onClick() {
        Intent intent = new Intent();
        if(filepath!=null){
            intent.putExtra("imageUrl",filepath);
        }
        setResult(PeopleApplication.IMAG_INTENT_BACK_NAME, intent);
        finish();
    }

    /**
     * 子activity用此方法代替
     * getSupportActionBar().setTitle("");
     */
    protected void initTitle() {
        if (toolbar != null) {
            toolbar.setTitle("人员拍照");
//            toolbar.setNavigationIcon(0);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            return;
        }
    }
}
