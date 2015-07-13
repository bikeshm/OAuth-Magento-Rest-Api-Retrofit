package com.ipragmatech.oauthmagentorestapi.services;


import com.ipragmatech.oauthmagentorestapi.model.Products;

import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Created by kapiljain on 11/07/15.
 */
public interface ProductService {
    @GET("/products")
    Products getAllProducts();

}
