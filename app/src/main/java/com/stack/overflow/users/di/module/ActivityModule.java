package com.stack.overflow.users.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.stack.overflow.users.StackOverflowApplication;
import com.stack.overflow.users.di.scopes.ActivityScope;
import com.stack.overflow.users.application.presenter.ReputationPresenter;
import com.stack.overflow.users.application.presenter.service.ServiceCall;
import com.stack.overflow.users.application.view.adapter.ReputationAdapter;
import com.stack.overflow.users.application.view.fragment.AllUsersFragment;
import com.stack.overflow.users.application.view.fragment.FavoriteUsersFragment;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {
    @Provides @ActivityScope
    AllUsersFragment provideAllUsersFragment() {
        return new AllUsersFragment();
    }
    @Provides @ActivityScope
    FavoriteUsersFragment provideFavoriteUsersFragment() {
        return new FavoriteUsersFragment();
    }
    @Provides @ActivityScope
    ReputationPresenter provideReputationPresenter(ServiceCall services, CompositeDisposable compositeDisposable) {
        return new ReputationPresenter(services, compositeDisposable);
    }
    @Provides @ActivityScope
    LinearLayoutManager provideLinearLayoutManager(StackOverflowApplication application) {
        return new LinearLayoutManager(application);
    }
    @Provides @ActivityScope
    ReputationAdapter provideReputationAdapter() {
        return new ReputationAdapter();
    }
}