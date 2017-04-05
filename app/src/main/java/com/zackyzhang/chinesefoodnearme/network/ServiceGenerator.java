package com.zackyzhang.chinesefoodnearme.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by lei on 3/28/17.
 */

public class ServiceGenerator {

    public static final String API_BASE_URL = "https://api.yelp.com/";
    public static final String DIRECTION_API_BASE_URL = "https://maps.googleapis.com/maps/api/directions";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .addInterceptor(logInterceptor());

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

//    private static Retrofit.Builder direction_builder =
//            new Retrofit.Builder()
//                    .baseUrl(DIRECTION_API_BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static YelpFusionAuthService getAccessToken() {
        builder.client(httpClient.build());
        retrofit = builder.build();
        return retrofit.create(YelpFusionAuthService.class);
    }

//    public static GoogleDirectionApiService createGoogleDirectionService() {
//        direction_builder.client(httpClient.build());
//        retrofit = direction_builder.build();
//        return retrofit.create(GoogleDirectionApiService.class);
//    }

    public static <S> S createService(Class<S> serviceClass) {
        builder.client(httpClient.build());
        retrofit = builder.build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass, final AccessToken authToken) {
        if (authToken != null) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }

    private static HttpLoggingInterceptor logInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.tag("OkHttp").d(message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return httpLoggingInterceptor;
    }

}
