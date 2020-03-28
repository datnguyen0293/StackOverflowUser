package com.stack.overflow.users.application.view;

import com.stack.overflow.users.application.model.Reputation;
import com.stack.overflow.users.base.BaseView;
import java.util.List;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */
public interface GetReputationView extends BaseView {
    void onGetReputations(List<Reputation> reputationList);
    void onNoReputations();
}