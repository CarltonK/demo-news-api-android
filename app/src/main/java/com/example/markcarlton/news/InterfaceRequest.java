package com.example.markcarlton.news;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Edward on 27/02/2017.
 */

interface InterfaceRequest {

   @GET("articles?source=the-next-web&sortBy=latest&apiKey=bdb7bf453b3140498dc1928c6b639434")
   Call<ResponseList> getArticles();

    @GET("sources?language=en&category=technology")
    Call<ResponseList> getTechnology();

    @GET("articles?source=techcrunch&sortBy=top&apiKey=bdb7bf453b3140498dc1928c6b639434")
    Call<ResponseList> getSpecifics();
}
