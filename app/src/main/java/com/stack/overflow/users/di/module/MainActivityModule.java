package com.stack.overflow.users.di.module;

import com.stack.overflow.users.application.view.fragment.AllUsersFragment;
import com.stack.overflow.users.application.view.fragment.FavoriteUsersFragment;
import com.stack.overflow.users.di.scopes.ActivityScope;
import com.stack.overflow.users.di.scopes.FragmentScope;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {
    @FragmentScope @ContributesAndroidInjector(modules = AllUserFragmentModule.class)
    abstract AllUsersFragment allUsersFragment();
    @FragmentScope @ContributesAndroidInjector(modules = FavoriteUserFragmentModule.class)
    abstract FavoriteUsersFragment favoriteUsersFragment();
    @Provides @ActivityScope
    static AllUsersFragment provideAllUsersFragment() {
        return new AllUsersFragment();
    }
    @Provides @ActivityScope
    static FavoriteUsersFragment provideFavoriteUsersFragment() {
        return new FavoriteUsersFragment();
    }
}