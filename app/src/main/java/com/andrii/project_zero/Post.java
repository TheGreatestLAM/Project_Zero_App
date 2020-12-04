package com.andrii.project_zero;

import com.google.gson.annotations.SerializedName;

public class Post
{
    @SerializedName("apiKey")
    private String apiKey;

    @SerializedName("modelName")
    private String modelName;

    @SerializedName("calledMethod")
    private String calledMethod;

    @SerializedName("methodProperties")
    MethodProperties methodProperties;

    @SerializedName("success")
    private String Success;

    @SerializedName("data")
    private Data[] Data;

    public Post(String apiKey, String modelName, String calledMethod)
    {
        this.apiKey = apiKey;
        this.modelName = modelName;
        this.calledMethod = calledMethod;
    }

    public Post(String apiKey, String modelName, String calledMethod, MethodProperties methodProperties)
    {
        this.apiKey = apiKey;
        this.modelName = modelName;
        this.calledMethod = calledMethod;
        this.methodProperties = methodProperties;
    }

    public int getDataArrayLength()
    {
        return Data.length;
    }

    public String getApiKey()
    {
        return apiKey;
    }

    public String getModelName()
    {
        return modelName;
    }

    public String getCalledMethod()
    {
        return calledMethod;
    }

    public MethodProperties getMethodProperties()
    {
        return methodProperties;
    }

    public String getSuccess()
    {
        return Success;
    }

    public com.andrii.project_zero.Data getData(int i)
    {
        return Data[i];
    }
}

