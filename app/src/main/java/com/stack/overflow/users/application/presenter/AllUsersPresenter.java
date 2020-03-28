package com.stack.overflow.users.application.presenter;

import com.stack.overflow.users.StackOverflowApplication;
import com.stack.overflow.users.application.model.UserItem;
import com.stack.overflow.users.application.presenter.service.ServiceCall;
import com.stack.overflow.users.application.view.GetAllUsersView;
import com.stack.overflow.users.base.BasePresenter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */

public class AllUsersPresenter extends BasePresenter<GetAllUsersView> {

    public AllUsersPresenter() {
        super();
        // Do nothing
    }

    public void getListUser(int page, int pageSize) {
        Objects.requireNonNull(view()).showLoadingDialog();
        mDisposable = mServices.getListUsers(page, pageSize, new ServiceCall.ApiCallback() {
            @Override
            public void responseSucceed(Object obj) {
                if (!((List<?>)obj).isEmpty()) {
                    List<UserItem> userItemList = new ArrayList<>();
                    for (Object o : (List<?>) obj) {
                        userItemList.add((UserItem) o);
                    }
                    Objects.requireNonNull(view()).onGetAllUsers(userItemList);
                } else {
                    Objects.requireNonNull(view()).onNoUsers();
                }
                Objects.requireNonNull(view()).hideLoadingDialog();
            }

            @Override
            public void responseFail(String errorMessage) {
                Objects.requireNonNull(view()).onNoUsers();
                Objects.requireNonNull(view()).hideLoadingDialog();
            }

            @Override
            public void callbackFail(Throwable throwable) {
                Objects.requireNonNull(view()).onNoUsers();
                Objects.requireNonNull(view()).hideLoadingDialog();
                getNetErrorConsumer(throwable);
            }
        });
        mCompositeDisposable.add(mDisposable);
    }
}