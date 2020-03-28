package com.stack.overflow.users;

import com.stack.overflow.users.di.components.DaggerApplicationComponent;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */

public class StackOverflowApplication extends DaggerApplication {

    @Override protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder().application(this).build();
    }
}