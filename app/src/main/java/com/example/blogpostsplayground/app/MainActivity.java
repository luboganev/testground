package com.example.blogpostsplayground.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_demoIntentBuilder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AwsumActivity.AwsumIntentBuilder.getBuilder()
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
    }
}
