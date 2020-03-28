package com.stack.overflow.users.application.presenter;

import com.stack.overflow.users.application.model.Reputation;
import com.stack.overflow.users.application.presenter.service.ServiceCall;
import com.stack.overflow.users.application.view.GetReputationView;
import com.stack.overflow.users.base.BasePresenter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */

public class ReputationPresenter extends BasePresenter<GetReputationView> {
    public ReputationPresenter() {
        super();
        // Do nothing
    }

    public void getListReputation(long userId, int page, int pageSize) {
        Objects.requireNonNull(view()).showLoadingDialog();
        mDisposable = mServices.getListReputations(userId, page, pageSize, new ServiceCall.ApiCallback() {
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