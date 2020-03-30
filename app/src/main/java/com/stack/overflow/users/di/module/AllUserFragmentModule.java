package com.stack.overflow.users.di.module;

import com.stack.overflow.users.application.presenter.FavoriteUsersPresenter;
import com.stack.overflow.users.application.view.adapter.FavoriteUsersAdapter;
import com.stack.overflow.users.di.scopes.FragmentScope;
import com.stack.overflow.users.application.presenter.AllUsersPresenter;
import com.stack.overflow.users.application.presenter.service.ServiceCall;
import com.stack.overflow.users.application.view.adapter.UsersAdapter;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class AllUserFragmentModule {

    @Provides @FragmentScope
    AllUsersPresenter provideAllUsersPresenter(ServiceCall services, CompositeDisposable compositeDisposable) {
        return new AllUsersPresenter(services, compositeDisposable);
    }
    @Provides @FragmentScope
    UsersAdapter provideUsersAdapter() {
        return new UsersAdapter();
    }

    @Provides @FragmentScope
    FavoriteUsersPresenter provideFavoriteUsersPresenter(ServiceCall services, CompositeDisposable compositeDisposable) {
        return new FavoriteUsersPresenter(services, compositeDisposable);
    }
    @Provides @FragmentScope
    FavoriteUsersAdapter provideFavoriteUsersAdapter() {
        return new FavoriteUsersAdapter();
    }
}