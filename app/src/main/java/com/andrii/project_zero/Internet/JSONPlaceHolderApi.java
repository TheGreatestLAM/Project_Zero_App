package com.andrii.project_zero.Internet;

import com.andrii.project_zero.Model.Post;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JSONPlaceHolderApi
{
    @POST("/v2.0/json/")
    public Call<Post> postData(@Body Post post);
}
