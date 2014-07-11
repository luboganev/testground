package com.example.blogpostsplayground.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;


public class HeadlessFragmentDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headlessfragmentdemo);

        HeadlessCounterFragment counterState = (HeadlessCounterFragment)getFragmentManager()
                .findFragmentByTag("counter_fragment");
        if(counterState == null) {
            counterState = new HeadlessCounterFragment();
            getFragmentManager().beginTransaction().add(counterState, "counter_fragment").commit();
        }

        if(savedInstanceState == null)
            counterState.setCounterTextView((TextView)findViewById(R.id.textView));

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

    public static class HeadlessCounterFragment extends Fragment {
        private SimpleCounterAsync mSimpleCounterAsync = new SimpleCounterAsync();
        private WeakReference<TextView> mCounterTextView;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        public void setCounterTextView(TextView textView) {
            mCounterTextView = new WeakReference<TextView>(textView);
        }

        public void startCounting() {
            if(mSimpleCounterAsync.getStatus() == AsyncTask.Status.RUNNING) return;
            if(mSimpleCounterAsync.getStatus() == AsyncTask.Status.FINISHED)
                mSimpleCounterAsync = new SimpleCounterAsync();
            mSimpleCounterAsync.execute();
        }

        public void stopCounting() {
            if(mSimpleCounterAsync.getStatus() != AsyncTask.Status.RUNNING) return;
            mSimpleCounterAsync.cancel(true);
        }

        private class SimpleCounterAsync extends AsyncTask<Void, Integer, Void> {
            private int mViewIsGoneCount = 0;

            @Override
            protected Void doInBackground(Void... voids) {
                int count = 0;

                while(!isCancelled()) {
                    count++;
                    publishProgress(count);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        return null;
                    }
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                TextView view = mCounterTextView.get();
                if(view != null) {
                    view.setText("Counter: " + values[0]);
                    mViewIsGoneCount = 0;
                } else {
                    mViewIsGoneCount++;
                    if(mViewIsGoneCount < 5) {
                        Toast.makeText(getActivity(), "TextView is not there for "
                                + mViewIsGoneCount + " time.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "TextView was gone for too long. " +
                                "Terminating the counter.", Toast.LENGTH_SHORT).show();
                        cancel(true);
                    }
                }
            }
        };
    }

}
