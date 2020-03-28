package com.stack.overflow.users.application.presenter.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stack.overflow.users.BuildConfig;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * @author dat nguyen
 * @since 2019 Sep 12
 */
public class ServiceFactory {

    private static ServiceApi mServiceApi = null;

    ServiceFactory() {
        // Do nothing
    }

    public static ServiceApi  createServiceApi() {
        if (mServiceApi == null) {
            mServiceApi = ServiceFactory.create();
        }
        return mServiceApi;
    }

    /**
     * Create service api
     * @return {@link ServiceApi}
     */
    public static ServiceApi create() {

        Gson gson = new GsonBuilder().serializeNulls().create();
        RxJava2CallAdapterFactory callAdapter = RxJava2CallAdapterFactory.create();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(BuildConfig.SERVER_URL);
        retrofitBuilder.client(createHttpClient());

        retrofitBuilder.addConverterFactory(GsonConverterFactory.create(gson));
        retrofitBuilder.addCallAdapterFactory(callAdapter);

        Retrofit retrofit = retrofitBuilder.build();

        return retrofit.create(ServiceApi.class);
    }

    /**
     * Initialize http client
     * @return {@link OkHttpClient}
     */
    private static OkHttpClient createHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request.Builder builder = original.newBuilder();
            builder.header("Accept", "application/json;charset=utf-8");
            builder.header("Accept-Language", "en");
            builder.method(original.method(), original.body()).build();
            Request request = builder.build();

            return chain.proceed(request);
        });

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptorBody = new HttpLoggingInterceptor();
            interceptorBody.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptorBody);
        }

        httpClient.connectTimeout(120, TimeUnit.SECONDS);
        httpClient.readTimeout(120, TimeUnit.SECONDS);
        httpClient.writeTimeout(120, TimeUnit.SECONDS);
        httpClient.hostnameVerifier((hostname, session) -> true);

        return httpClient.build();
    }
}