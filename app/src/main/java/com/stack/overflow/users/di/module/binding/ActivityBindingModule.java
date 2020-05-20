package com.stack.overflow.users.di.module.binding;

import android.content.Context;
import com.stack.overflow.users.StackOverflowApplication;
import com.stack.overflow.users.application.view.activity.AllUsersActivity;
import com.stack.overflow.users.di.module.AllUserActivityModule;
import com.stack.overflow.users.di.module.MainActivityModule;
import com.stack.overflow.users.di.module.ReputaionActivityModule;
import com.stack.overflow.users.di.scopes.ActivityScope;
import com.stack.overflow.users.application.view.activity.MainActivity;
import com.stack.overflow.users.application.view.activity.ReputationActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ActivityScope @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivity();
    @ActivityScope @ContributesAndroidInjector(modules = ReputaionActivityModule.class)
    abstract ReputationActivity reputationActivity();
    @ActivityScope @ContributesAndroidInjector(modules = AllUserActivityModule.class)
    abstract AllUsersActivity allUsersActivity();
    @Binds
    abstract Context bindApplication(StackOverflowApplication application);
}