package com.stack.overflow.users.application.presenter;

import com.stack.overflow.users.application.model.Reputation;
import com.stack.overflow.users.application.presenter.service.ServiceCall;
import com.stack.overflow.users.application.view.GetReputationView;
import com.stack.overflow.users.base.BasePresenter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */

public class ReputationPresenter extends BasePresenter<GetReputationView> {
    public ReputationPresenter(ServiceCall services, CompositeDisposable compositeDisposable) {
        mServices = services;
        mCompositeDisposable = compositeDisposable;
    }

    public void getListReputation(long userId, int page, int pageSize, String site, String creation, String sort) {
        Objects.requireNonNull(view()).showLoadingDialog();
        mDisposable = mServices.getListReputations(userId, page, pageSize, site, creation, sort, new ServiceCall.ApiCallback() {
            @Override
            public void responseSucceed(Object obj) {
                if (!((List<?>)obj).isEmpty()) {
                    List<Reputation> reputationList = new ArrayList<>();
                    for (Object o : (List<?>) obj) {
                        reputationList.add((Reputation) o);
                    }
                    Objects.requireNonNull(view()).onGetReputations(reputationList);
                } else {
                    Objects.requireNonNull(view()).onNoReputations();
                }
                Objects.requireNonNull(view()).hideLoadingDialog();
            }

            @Override
            public void responseFail(String errorMessage) {
                Objects.requireNonNull(view()).onNoReputations();
                Objects.requireNonNull(view()).hideLoadingDialog();
            }

            @Override
            public void callbackFail(Throwable throwable) {
                Objects.requireNonNull(view()).onNoReputations();
                Objects.requireNonNull(view()).hideLoadingDialog();
                getNetErrorConsumer(throwable);
            }
        });
        mCompositeDisposable.add(mDisposable);
    }
}