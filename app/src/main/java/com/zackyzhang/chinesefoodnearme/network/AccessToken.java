package com.zackyzhang.chinesefoodnearme.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lei on 3/28/17.
 */

public class AccessToken {

    /**
     * access_token : your access token
     * token_type : bearer
     * expires_in : 15552000
     */

    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("expires_in")
    private int expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
