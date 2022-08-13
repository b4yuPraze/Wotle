package com.wotle.wonderfulshuttle.API;

import com.wotle.wonderfulshuttle.Model.ReponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    @FormUrlEncoded
    @POST("restful_api_auth/signin")
    Call<ReponseModel> ardSignin(
            @Field("email") String email,
            @Field("pass") String pass
    );
}
