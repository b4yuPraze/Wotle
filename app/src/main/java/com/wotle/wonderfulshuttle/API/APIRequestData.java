package com.wotle.wonderfulshuttle.API;

import com.wotle.wonderfulshuttle.Model.ReponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIRequestData {
    @GET("restful_api_auth/signin")
    Call<ReponseModel> ardSignin();
}
