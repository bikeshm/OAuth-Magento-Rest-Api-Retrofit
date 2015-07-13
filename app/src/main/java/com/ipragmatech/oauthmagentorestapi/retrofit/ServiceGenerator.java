package com.ipragmatech.oauthmagentorestapi.retrofit;

import com.google.api.client.auth.oauth.OAuthParameters;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.SimpleXMLConverter;

/**
 * Created by kapiljain on 11/07/15.
 */
public class ServiceGenerator {
    private static RestAdapter.Builder builder = new RestAdapter.Builder();

    // No need to instantiate this class.
    private ServiceGenerator() {
    }




    public static <S> S createService(Class<S> serviceClass, String baseUrl, final OAuthParameters oAuthParams) {
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new OAuthInterceptor(oAuthParams));
        builder.setClient(new OkClient(client));
        builder.setEndpoint(baseUrl).setLogLevel(RestAdapter.LogLevel.FULL);
        builder.setConverter(new SimpleXMLConverter());
        RestAdapter adapter = builder.build();
        return adapter.create(serviceClass);
    }
}