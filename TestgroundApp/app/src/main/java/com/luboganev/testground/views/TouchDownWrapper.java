package com.luboganev.testground.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by luboganev on 11/10/14.
 */
public class TouchDownWrapper extends FrameLayout {
    private boolean mIsTouched = false;
    private boolean mTouchBlocking = false;

    public TouchDownWrapper(Context context) {
        super(context);
    }

    public TouchDownWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchDownWrapper(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsTouched = true;
                if(mOnTouchDownListener != null) mOnTouchDownListener.onTouchDownStateChanged(mIsTouched);
                break;
            case MotionEvent.ACTION_UP:
                mIsTouched = false;
                if(mOnTouchDownListener != null) mOnTouchDownListener.onTouchDownStateChanged(mIsTouched);
                break;
        }

        if(mTouchBlocking) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    private OnTouchDownStateChangedListener mOnTouchDownListener;

    public void setOnTouchDownStateChangedListener(OnTouchDownStateChangedListener listener) {
        mOnTouchDownListener = listener;
    }

    public boolean isTouched() {
        return mIsTouched;
    }

    public boolean isTouchBlockingEnabled() {
        return mTouchBlocking;
    }

    public void setTouchBlockingEnabled(boolean touchBlocking) {
        this.mTouchBlocking = touchBlocking;
    }

    public static interface OnTouchDownStateChangedListener {
        public void onTouchDownStateChanged(boolean isTouched);
    }

}
