package com.ipragmatech.oauthmagentorestapi.retrofit;

import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.http.GenericUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by kapiljain on 12/07/15.
 */
public class OAuthInterceptor implements Interceptor {

    private final OAuthParameters oAuthParams;

    public OAuthInterceptor(OAuthParameters oAuthParams) {
        this.oAuthParams = oAuthParams;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        GenericUrl requestUrl = new GenericUrl(originalRequest.urlString());
        oAuthParams.computeNonce();
        oAuthParams.computeTimestamp();
        try {
            oAuthParams.computeSignature("GET", requestUrl);
            Request compressedRequest = originalRequest.newBuilder()
                    .header("Authorization", oAuthParams.getAuthorizationHeader())
                    .header("Accept","application/xml")
                    .method(originalRequest.method(),originalRequest.body())
                    .build();
            return chain.proceed(compressedRequest);
        } catch (GeneralSecurityException e) {
        }


        return chain.proceed(originalRequest);
    }
}
