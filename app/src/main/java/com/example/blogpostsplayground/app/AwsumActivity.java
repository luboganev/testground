package com.example.blogpostsplayground.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;


public class AwsumActivity extends Activity {
    private static String INTENT_EXTRA_TITLE = "title";
    private static String INTENT_EXTRA_PAGE_NUMBER = "page_number";

    public static class AwsumIntentBuilder {
        private Bundle mExtras;

        private AwsumIntentBuilder() {
            mExtras = new Bundle();
        }

        public static AwsumIntentBuilder getBuilder() {
            AwsumIntentBuilder builder = new AwsumIntentBuilder();
            return builder;
        }

        public AwsumIntentBuilder withTitle(String title) {
            mExtras.putString(INTENT_EXTRA_TITLE, title);
            return this;
        }

        public AwsumIntentBuilder withPageNumber(int pageNumber) {
            mExtras.putInt(INTENT_EXTRA_PAGE_NUMBER, pageNumber);
            return this;
        }

        public Intent build(Context ctx) {
            Intent i = new Intent(ctx, AwsumActivity.class);
            i.putExtras(mExtras);
            return i;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awsum);

        String title = "";

        if(getIntent().hasExtra(INTENT_EXTRA_TITLE)) {
            title += "Title:" + getIntent().getStringExtra(INTENT_EXTRA_TITLE);
        }

        if(getIntent().hasExtra(INTENT_EXTRA_PAGE_NUMBER)) {
            title += " Page №" + getIntent().getIntExtra(INTENT_EXTRA_PAGE_NUMBER, 0);
        }

        if(TextUtils.isEmpty(title)) {
            setTitle(getResources().getString(R.string.title_activity_awsum));
        } else {
            setTitle(title);
        }
    }
}
