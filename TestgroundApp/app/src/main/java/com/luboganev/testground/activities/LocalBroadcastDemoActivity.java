package com.luboganev.testground.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.luboganev.testground.LocalMessenger;
import com.luboganev.testground.R;


public class LocalBroadcastDemoActivity extends Activity implements LocalMessenger.OnReceiveBroadcastListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localbroadcastdemo);

        LocalMessenger.getInstance().setListener(getApplicationContext(), "com.example.blogpostsplayground.app.ACTION_AWSUM_MESSAGE", this);

        findViewById(R.id.btn_send_messages).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = new Bundle();
                extras.putString("message", "Me block it!");
                LocalMessenger.getInstance().sendBlockingBroadcast(getApplicationContext(), "com.example.blogpostsplayground.app.ACTION_AWSUM_MESSAGE", extras);

                extras.putString("message", "Me like it!");
                LocalMessenger.getInstance().sendBroadcast(getApplicationContext(), "com.example.blogpostsplayground.app.ACTION_AWSUM_MESSAGE", extras);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalMessenger.getInstance().removeListener(getApplicationContext(), "com.example.blogpostsplayground.app.ACTION_AWSUM_MESSAGE");
    }

    @Override
    public void onReceiveBroadcast(Bundle extras) {
        Toast.makeText(LocalBroadcastDemoActivity.this, "Awsum broadcast message: " + extras.getString("message"), Toast.LENGTH_SHORT).show();
    }
}
