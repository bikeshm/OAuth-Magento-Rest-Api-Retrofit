package com.ipragmatech.oauthmagentorestapi.utils;


public class Constants {

	public static final String CONSUMER_KEY 	= "b3563b92822708750b22ab2b009e91ad";
	public static final String CONSUMER_SECRET 	= "495611afb176a2db0e3a008b7908f073";

	public static final String BASE_URL 		="http://gemtree.ipragmatech.com/";

	public static final String REQUEST_URL 		= BASE_URL + "oauth/initiate";
	public static final String ACCESS_URL 		= BASE_URL + "oauth/token";
	public static final String AUTHORIZE_URL 	= BASE_URL + "oauth/authorize";
    public static final String API_REQUEST 		= BASE_URL + "api/rest/";

	public static final String PRODUCT_API_REQUEST 		=   API_REQUEST+"products";
	
	public static final String ENCODING 		= "UTF-8";

    public static final String OAUTH_CALLBACK_URL = "http://localhost/";

}
