package com.zackyzhang.chinesefoodnearme.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lei on 3/28/17.
 */

public class AuthenticationInterceptor implements Interceptor {

    private AccessToken authToken;

    public AuthenticationInterceptor(AccessToken token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("Authorization", authToken.getTokenType() + " " + authToken.getAccessToken());

        Request request = builder.build();
        return chain.proceed(request);
    }
}
