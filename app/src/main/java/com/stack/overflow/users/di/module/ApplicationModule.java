package com.stack.overflow.users.di.module;

import com.stack.overflow.users.StackOverflowApplication;
import com.stack.overflow.users.application.database.SQLiteHelper;
import com.stack.overflow.users.application.presenter.service.ServiceApi;
import com.stack.overflow.users.application.presenter.service.ServiceCall;
import com.stack.overflow.users.application.presenter.service.ServiceFactory;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ApplicationModule {

    @Provides @Singleton
    ServiceApi provideServiceApi() {
        return ServiceFactory.create();
    }

    @Provides @Singleton
    SQLiteHelper provideSqLiteHelper(StackOverflowApplication application) {
        return new SQLiteHelper(application);
    }

    @Provides @Singleton
    ServiceCall provideServiceCall(ServiceApi serviceApi, SQLiteHelper sqLiteHelper) {
        return new ServiceCall(serviceApi, sqLiteHelper);
    }

    @Provides @Singleton
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}