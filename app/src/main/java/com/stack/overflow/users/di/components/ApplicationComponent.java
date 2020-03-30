package com.stack.overflow.users.di.components;

import com.stack.overflow.users.StackOverflowApplication;
import com.stack.overflow.users.di.module.ApplicationModule;
import com.stack.overflow.users.di.module.binding.ActivityBindingModule;
import javax.inject.Singleton;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton @Component(modules = {
        AndroidInjectionModule.class,
        ActivityBindingModule.class,
        ApplicationModule.class})
public interface ApplicationComponent extends AndroidInjector<StackOverflowApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        ApplicationComponent.Builder application(StackOverflowApplication application);

        ApplicationComponent build();
    }
}