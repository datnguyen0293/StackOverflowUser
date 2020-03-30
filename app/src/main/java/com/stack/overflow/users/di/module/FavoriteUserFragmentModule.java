package com.stack.overflow.users.di.module;

import com.stack.overflow.users.application.presenter.FavoriteUsersPresenter;
import com.stack.overflow.users.application.presenter.service.ServiceCall;
import com.stack.overflow.users.application.view.adapter.FavoriteUsersAdapter;
import com.stack.overflow.users.di.scopes.FragmentScope;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
@Module
public class FavoriteUserFragmentModule {

    @Provides @FragmentScope
    FavoriteUsersPresenter provideFavoriteUsersPresenter(ServiceCall services, CompositeDisposable compositeDisposable) {
        return new FavoriteUsersPresenter(services, compositeDisposable);
    }
    @Provides @FragmentScope
    FavoriteUsersAdapter provideFavoriteUsersAdapter() {
        return new FavoriteUsersAdapter();
    }
}