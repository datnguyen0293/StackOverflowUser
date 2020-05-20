package com.stack.overflow.users.di.module;

import com.stack.overflow.users.application.presenter.AllUsersPresenter2;
import com.stack.overflow.users.application.presenter.service.ServiceCall;
import com.stack.overflow.users.application.view.adapter.UsersAdapter;
import com.stack.overflow.users.di.scopes.ActivityScope;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class AllUserActivityModule {
    @Provides @ActivityScope
    AllUsersPresenter2 provideAllUsersPresenter(ServiceCall services, CompositeDisposable compositeDisposable) {
        return new AllUsersPresenter2(services, compositeDisposable);
    }

    @Provides @ActivityScope
    UsersAdapter provideUsersAdapter() {
        return new UsersAdapter();
    }
}