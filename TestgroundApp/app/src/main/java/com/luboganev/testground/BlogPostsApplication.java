package com.luboganev.testground;

import android.app.Application;
import android.content.Context;

/**
 * Created by luboganev on 01/06/14.
 */
public class BlogPostsApplication extends Application {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        BlogPostsApplication.context = getApplicationContext();
    }

    public static Context getStaticAppContext() {
        return BlogPostsApplication.context;
    }
}
