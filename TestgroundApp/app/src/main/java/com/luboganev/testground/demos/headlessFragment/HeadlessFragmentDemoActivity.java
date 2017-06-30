package com.luboganev.testground.demos.headlessFragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.luboganev.testground.R;


public class HeadlessFragmentDemoActivity extends AppCompatActivity {

    private HeadlessCounterFragment mHeadlessCounterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headlessfragmentdemo);

        mHeadlessCounterFragment = (HeadlessCounterFragment)getFragmentManager()
                .findFragmentByTag("counter_fragment");
        if(mHeadlessCounterFragment == null) {
            mHeadlessCounterFragment = new HeadlessCounterFragment();
            getFragmentManager().beginTransaction().add(mHeadlessCounterFragment, "counter_fragment").commit();
        }

        if(savedInstanceState == null) {
            // Setting the TextView for the count only initially
            mHeadlessCounterFragment.setCounterTextView((TextView) findViewById(R.id.textView));
        }

        findViewById(R.id.btn_startCounting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HeadlessCounterFragment counterState = (HeadlessCounterFragment)getFragmentManager()
                        .findFragmentByTag("counter_fragment");
                if(counterState != null) counterState.startCounting();
            }
        });

        findViewById(R.id.btn_stopCounting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HeadlessCounterFragment counterState = (HeadlessCounterFragment)getFragmentManager()
                        .findFragmentByTag("counter_fragment");
                if(counterState != null) counterState.stopCounting();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Making sure we clean references on destroy
        if(mHeadlessCounterFragment != null) {
            mHeadlessCounterFragment.setCounterTextView(null);
            mHeadlessCounterFragment = null;
        }
    }
}
