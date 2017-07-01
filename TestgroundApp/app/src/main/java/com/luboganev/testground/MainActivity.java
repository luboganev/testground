package com.luboganev.testground;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.luboganev.testground.demos.headlessFragment.HeadlessFragmentDemoActivity;
import com.luboganev.testground.demos.imageScale.ImageScaleDemoActivity;
import com.luboganev.testground.demos.intentBuilder.IntentBuilderDemoActivity;
import com.luboganev.testground.demos.ipcMessenger.client.MessengerClientDemoActivity;
import com.luboganev.testground.demos.localBroadcast.LocalBroadcastDemoActivity;
import com.luboganev.testground.demos.pendingAlarms.PendingAlarmsDemoActivity;
import com.luboganev.testground.demos.touchDown.TouchDownDemoActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_demoIntentBuilder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(IntentBuilderDemoActivity.AwsumIntentBuilder.getBuilder()
                        .withTitle("test")
                        .withPageNumber(1337)
                        .build(MainActivity.this));
            }
        });


        findViewById(R.id.btn_demoLocalBroadcastsCenter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LocalBroadcastDemoActivity.class));
            }
        });

        findViewById(R.id.btn_demoHeadlessFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HeadlessFragmentDemoActivity.class));
            }
        });

        findViewById(R.id.btn_demoImageScaleType).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ImageScaleDemoActivity.class));
            }
        });

        findViewById(R.id.btn_demoTouchdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TouchDownDemoActivity.class));
            }
        });

        findViewById(R.id.btn_demoPendinAlarms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PendingAlarmsDemoActivity.class));
            }
        });

        findViewById(R.id.btn_demoIPCMessenger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MessengerClientDemoActivity.class));
            }
        });
    }

    public void openRelatedPost(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse((String)v.getTag()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
