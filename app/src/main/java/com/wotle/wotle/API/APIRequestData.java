package com.wotle.wotle.API;

import com.wotle.wotle.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIRequestData {
    @FormUrlEncoded
    @POST("Restful_API_auth/SignInWithEmail")
    Call<ResponseModel> ardSignin(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @FormUrlEncoded
    @POST("Restful_API_auth/signInWithGoogle")
    Call<ResponseModel> insert(
            @Field("email") String email,
            @Field("no_hp") String no_hp,
            @Field("nama_lengkap") String nama_lengkap,
            @Field("foto_profile") String foto_profile
    );

    @FormUrlEncoded
    @POST("Restful_API_auth/registerWithEmail")
    Call<ResponseModel> ardSignUp(
            @Field("fullname") String fullname,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("Restful_API_users/getPersonalInfo")
    Call<ResponseModel> getPersonalInfo(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("Restful_API_users/send_email")
    Call<ResponseModel> verifEmail(
            @Field("email") String email,
            @Field("otp") String otp
    );

    @FormUrlEncoded
    @POST("Restful_API_users/updateVerifEmail")
    Call<ResponseModel> updateVerifEmail(
            @Field("email") String email
    );


}
