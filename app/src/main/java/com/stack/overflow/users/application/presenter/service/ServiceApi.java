package com.stack.overflow.users.application.presenter.service;

import com.stack.overflow.users.application.model.response.ReputationResponse;
import com.stack.overflow.users.application.model.response.UsersResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */

public interface ServiceApi {

    @GET("users")
    Observable<UsersResponse> getUsers(@Query("page") int page, @Query("pagesize") int pageSize, @Query("site") String site, @Query("sort") String sort, @Query("order") String order, @Query("key") String key);

    @GET("users/{userId}/reputation-history")
    Observable<ReputationResponse> getReputations(@Path("userId") long userId, @Query("page") int page, @Query("pagesize") int pageSize, @Query("site") String site, @Query("sort") String sort, @Query("order") String order, @Query("key") String key);
}