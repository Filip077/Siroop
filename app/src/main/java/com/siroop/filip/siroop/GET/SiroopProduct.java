package com.siroop.filip.siroop.GET;

import com.siroop.filip.siroop.Description.ProductAttributes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by filip on 15.2.18.
 */

public interface SiroopProduct {

    @GET("/product/search/")
    Call<List<ProductAttributes>> products(@Query("query") String query,
                                          @Query("limit") Integer limit,
                                          @Header("x-api-key") String apikey);


}
