package com.ipragmatech.oauthmagentorestapi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.ipragmatech.oauthmagentorestapi.model.Products;
import com.ipragmatech.oauthmagentorestapi.retrofit.ServiceGenerator;
import com.ipragmatech.oauthmagentorestapi.services.ProductService;
import com.ipragmatech.oauthmagentorestapi.utils.Constants;
import com.ipragmatech.oauthmagentorestapi.utils.LocalCredentialStore;


public class MainActivity extends BaseActivity {

    private ProgressBar progress;
    TextView serverRespText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startedBtn = (Button) findViewById(R.id.login_btn_get_started);
        Button catalogBtn = (Button) findViewById(R.id.catalog_btn);
        serverRespText = (TextView) findViewById(R.id.server_response);
        localCredentialStore = new LocalCredentialStore(prefs);
        //Hide the button based on authtoken
        if (localCredentialStore.getToken().getAuthToken().isEmpty()) {
            startedBtn.setVisibility(View.VISIBLE);
            catalogBtn.setVisibility(View.GONE);
        } else {
            startedBtn.setVisibility(View.GONE);
            catalogBtn.setVisibility(View.VISIBLE);
        }
        //Open the webview to allow user to access the login page
        startedBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent().setClass(v.getContext(), WebActivity.class));
            }
        });

        //Fetch the json objects after authorization
        catalogBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                serverRespText.setText("");
                new DownloadJson(Constants.PRODUCT_API_REQUEST, getConsumer()).execute();
            }
        });
        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);
        progress.setIndeterminate(true);
        progress.setProgress(0);
    }


    // DownloadJSON AsyncTask
    private class DownloadJson extends AsyncTask {
        private String url;
        private OAuthParameters consumer;
        public HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

        public DownloadJson(String apiRequest, OAuthParameters consumer) {
            this.url = apiRequest;
            this.consumer = consumer;
        }

        @Override
        protected Object doInBackground(Object[] params) {

            return sendRetrofitRequest();
           // return sendGoogleApiRequest();
        }

        private Object sendRetrofitRequest() {

            try {
                ProductService productService = ServiceGenerator.createService(
                        ProductService.class, Constants.API_REQUEST,consumer);
                Products products = productService.getAllProducts();
                return "Products :"+products.getProduct().get(0).getName();
            }catch (Exception e){
                Log.e(TAG,e.getMessage(),e);
            }
            return null;
        }

        private Object sendGoogleApiRequest() {
            try {

                GenericUrl requestUrl = new GenericUrl(url);

                HttpRequestFactory requestFactory = HTTP_TRANSPORT
                        .createRequestFactory(new HttpRequestInitializer() {
                            @Override
                            public void initialize(HttpRequest request) {

                                request.getHeaders().setAccept("application/xml");
                            }
                        });

                HttpRequest request = requestFactory.buildGetRequest(requestUrl);

                consumer.initialize(request);


                Log.d(TAG, "Calling server with url:" + url);
                HttpResponse response = request.execute();
                Log.d(TAG, request.getHeaders().getAuthorization());
                if (response.isSuccessStatusCode()) {
                    return response.parseAsString();
                } else {
                    Log.w(TAG, "Issue with the server call: " + response.getStatusMessage());
                }

            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            progress.setProgress(100);
            progress.setVisibility(View.GONE);
            Log.d(TAG, String.valueOf(result));
            serverRespText.setText(String.valueOf(result));
        }
    }
}
