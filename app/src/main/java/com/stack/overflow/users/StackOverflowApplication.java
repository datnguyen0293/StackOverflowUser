package com.stack.overflow.users;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */

public class StackOverflowApplication extends Application {

    private static StackOverflowApplication mInstance = null;
    private Scheduler mSubscribeScheduler = null;
    private Scheduler mObserveScheduler = null;

    public Scheduler subscribeScheduler() {
        if (mSubscribeScheduler == null) {
            mSubscribeScheduler = Schedulers.io();
        }
        return mSubscribeScheduler;
    }

    public Scheduler observeScheduler() {
        if (mObserveScheduler == null) {
            mObserveScheduler = AndroidSchedulers.mainThread();
        }
        return mObserveScheduler;
    }

    public static StackOverflowApplication getInstance() {
        if (mInstance != null) {
            return mInstance;
        }
        throw new NullPointerException("Must call setInstance(this) in onCreate() event.");
    }

    public static void setInstance(StackOverflowApplication instance) {
        StackOverflowApplication.mInstance = instance;
    }

    @Override public void onCreate() {
        super.onCreate();
        setInstance(this);
    }

    public boolean isNetWorkNotConnected() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo == null || !networkInfo.isConnectedOrConnecting();
    }
}