package com.andrii.project_zero;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JSONPlaceHolderApi
{
    @POST("/v2.0/json/")
    public Call<Post> postData(@Body Post post);
}
