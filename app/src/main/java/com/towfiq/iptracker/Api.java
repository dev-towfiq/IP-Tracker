package com.towfiq.iptracker;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("json")
    Call<Model> getMethod();
}