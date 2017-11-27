package xyz.no87.mywallpaper.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by linchen on 2017/11/27.
 */

public class BaseApplication extends Application {

    private static Application instance;

    private Context activityContext;

    public static Application getInstance() {
        if (instance == null) {
            synchronized (BaseApplication.class) {
                if (instance == null) {
                    instance = new Application();
                }
            }
        }
        return instance;
    }

    public Context getCurrentActivityContext() {
        return activityContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    protected void init() {
        instance = this;

        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                activityContext = activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }



}
