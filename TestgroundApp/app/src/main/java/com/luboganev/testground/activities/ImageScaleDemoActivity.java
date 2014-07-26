package com.luboganev.testground.activities;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.luboganev.testground.R;
import com.luboganev.testground.Utils;

public class ImageScaleDemoActivity extends Activity {

    private ImageView ivContainer;
    private Spinner spImageSource;
    private Spinner spScaleType;
    private SeekBar sbContainerWidth;
    private SeekBar sbContainerHeight;
    private CheckBox cbContainerWidthWrapContent;
    private CheckBox cbContainerHeightWrapContent;
    private TextView tvContainerWidthLabel;
    private TextView tvContainerHeightLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_scale_demo);

        getActionBar().hide();

        spImageSource = (Spinner) findViewById(R.id.sp_imageSource);
        spScaleType = (Spinner) findViewById(R.id.sp_scaleType);
        ivContainer = (ImageView) findViewById(R.id.iv_container);
        sbContainerWidth = (SeekBar) findViewById(R.id.sb_custom_width);
        sbContainerHeight = (SeekBar) findViewById(R.id.sb_custom_height);
        cbContainerWidthWrapContent = (CheckBox) findViewById(R.id.cb_width_wrap_content);
        cbContainerHeightWrapContent = (CheckBox) findViewById(R.id.cb_height_wrap_content);
        tvContainerWidthLabel = (TextView) findViewById(R.id.tv_width_controller_label);
        tvContainerHeightLabel = (TextView) findViewById(R.id.tv_height_controller_label);

        spImageSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TypedArray imgs = getResources().obtainTypedArray(R.array.array_scaleType_demo_images);
                ivContainer.setImageResource(imgs.getResourceId(position, -1));
                imgs.recycle();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spScaleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ivContainer.setScaleType(getScaleType(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spImageSource.setSelection(0);
        spScaleType.setSelection(0);

        refreshImageViewContainer();

        cbContainerWidthWrapContent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                refreshImageViewContainer();
            }
        });

        cbContainerHeightWrapContent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                refreshImageViewContainer();
            }
        });

        sbContainerWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                refreshImageViewContainer();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        sbContainerHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                refreshImageViewContainer();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void refreshImageViewContainer() {
        if(cbContainerHeightWrapContent.isChecked()) {
            ivContainer.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            tvContainerHeightLabel.setText("Container height:");
        } else {
            ivContainer.getLayoutParams().height = Utils.getPixelsFromDips(getApplicationContext(), sbContainerHeight.getProgress());
            tvContainerHeightLabel.setText("Container height:" + sbContainerHeight.getProgress());
        }

        if(cbContainerWidthWrapContent.isChecked()) {
            ivContainer.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            tvContainerWidthLabel.setText("Container width:");
        } else {
            ivContainer.getLayoutParams().width = Utils.getPixelsFromDips(getApplicationContext(), sbContainerWidth.getProgress());
            tvContainerWidthLabel.setText("Container width:" + sbContainerWidth.getProgress());
        }
        ivContainer.requestLayout();
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
