package com.luboganev.testground.demos.touchDown;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.luboganev.testground.R;

/**
 * Created by luboganev on 11/10/14.
 */
public class TouchDownDemoActivity extends AppCompatActivity {
    private TouchDownWrapper mTouchDownWrapper;
    private Button mWrappedButton;
    private CheckBox mStealTouch;
    private TextView mTouchdownStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touchdown_demo);

        mTouchDownWrapper = (TouchDownWrapper) findViewById(R.id.untouchable_wrapper);
        mWrappedButton = (Button) findViewById(R.id.btn_wrapped_untouchable);
        mStealTouch = (CheckBox) findViewById(R.id.cb_steal_touch);
        mTouchdownStatus = (TextView) findViewById(R.id.touchdown_status);

        mTouchDownWrapper.setOnTouchDownStateChangedListener(new TouchDownWrapper.OnTouchDownStateChangedListener() {
            @Override
            public void onTouchDownStateChanged(boolean isTouched) {
                updateTouchableStatusText(mTouchDownWrapper.isTouched());
            }
        });

        mStealTouch.setChecked(mTouchDownWrapper.isTouchBlockingEnabled());
        mStealTouch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mTouchDownWrapper.setTouchBlockingEnabled(isChecked);
            }
        });

        updateTouchableStatusText(mTouchDownWrapper.isTouched());

        mWrappedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TouchDownDemoActivity.this, "You have clicked the button!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTouchableStatusText(boolean isTouched) {
        mTouchdownStatus.setText(isTouched ? "Touchdown!" : "Not currently touched");
    }
}
