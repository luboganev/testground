package com.example.blogpostsplayground.app;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

public class ImageScaleDemoActivity extends Activity {

    private ImageView ivContainerLandscape;
    private ImageView ivContainerPortrait;
    private ImageView ivContainerSquare;
    private Spinner spImageSource;
    private Spinner spScaleType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_scale_demo);

        getActionBar().hide();

        spImageSource = (Spinner) findViewById(R.id.sp_imageSource);
        spScaleType = (Spinner) findViewById(R.id.sp_scaleType);
        ivContainerLandscape = (ImageView) findViewById(R.id.iv_container_landscape);
        ivContainerPortrait = (ImageView) findViewById(R.id.iv_container_portrait);
        ivContainerSquare = (ImageView) findViewById(R.id.iv_container_square);

        spImageSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TypedArray imgs = getResources().obtainTypedArray(R.array.array_scaleType_demo_images);
                ivContainerLandscape.setImageResource(imgs.getResourceId(position, -1));
                ivContainerPortrait.setImageResource(imgs.getResourceId(position, -1));
                ivContainerSquare.setImageResource(imgs.getResourceId(position, -1));
                imgs.recycle();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spScaleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ivContainerLandscape.setScaleType(getScaleType(position));
                ivContainerPortrait.setScaleType(getScaleType(position));
                ivContainerSquare.setScaleType(getScaleType(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spImageSource.setSelection(0);
        spScaleType.setSelection(0);
    }

    private ImageView.ScaleType getScaleType(int type) {
        switch (type) {
            case 0:
                return ImageView.ScaleType.MATRIX;
            case 1:
                return ImageView.ScaleType.FIT_XY;
            case 2:
                return ImageView.ScaleType.FIT_START;
            case 3:
                return ImageView.ScaleType.FIT_CENTER;
            case 4:
                return ImageView.ScaleType.FIT_END;
            case 5:
                return ImageView.ScaleType.CENTER;
            case 6:
                return ImageView.ScaleType.CENTER_CROP;
            case 7:
                return ImageView.ScaleType.CENTER_INSIDE;
        }
        return ImageView.ScaleType.CENTER_INSIDE;
    }
}
