package com.stack.overflow.users.di.module.binding;

import android.content.Context;
import com.stack.overflow.users.StackOverflowApplication;
import com.stack.overflow.users.di.module.ActivityModule;
import com.stack.overflow.users.di.module.FragmentModule;
import com.stack.overflow.users.di.scopes.ActivityScope;
import com.stack.overflow.users.di.scopes.FragmentScope;
import com.stack.overflow.users.application.view.activity.MainActivity;
import com.stack.overflow.users.application.view.activity.ReputationActivity;
import com.stack.overflow.users.application.view.fragment.AllUsersFragment;
import com.stack.overflow.users.application.view.fragment.FavoriteUsersFragment;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BindingModule {
    @ActivityScope @ContributesAndroidInjector(modules = ActivityModule.class)
    abstract MainActivity mainActivity();
    @ActivityScope @ContributesAndroidInjector(modules = ActivityModule.class)
    abstract ReputationActivity reputationActivity();
    @FragmentScope @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract AllUsersFragment allUsersFragment();
    @FragmentScope @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract FavoriteUsersFragment favoriteUsersFragment();
    @Binds
    abstract Context bindApplication(StackOverflowApplication application);
}