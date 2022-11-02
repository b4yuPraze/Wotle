package com.wotle.wotle.API;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wotle.wotle.Auth.Login;
import com.wotle.wotle.Auth.OneTimePassword;
import com.wotle.wotle.Auth.SignUp;
import com.wotle.wotle.Customer.Customer;
import com.wotle.wotle.Driver.Driver;
import com.wotle.wotle.GenerateOTP.OTP;
import com.wotle.wotle.Model.DataModel;
import com.wotle.wotle.Model.ResponseModel;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrefManager {
    List<DataModel> dataUser;
    Context context;

    public PrefManager(Context context){
        this.context = context;
    }

    public void saveUser(List<DataModel> dataUser){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userLogged", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dataUser);
        editor.putString("dataUser", json);
        editor.apply();
        editor.commit();
        if (dataUser.get(0).getRole().equals("Customer") && dataUser.get(0).getEmail_verif().equals("valid")){
            context.startActivity(new Intent(context, Customer.class));
            ((Activity) context).finish();
        }else if (dataUser.get(0).getRole().equals("Driver") && dataUser.get(0).getEmail_verif().equals("valid")){
            context.startActivity(new Intent(context, Driver.class));
            ((Activity) context).finish();
        }else{
            String email = dataUser.get(0).getEmail();
            Intent intent = new Intent(context, OneTimePassword.class);
            intent.putExtra("email", email);
            logout();
            context.startActivity(intent);
            Toast.makeText(context, "Silahkan verifikasi E-Mail terlebih dahulu!", Toast.LENGTH_SHORT).show();
//            context.startActivity(new Intent(context, OneTimePassword.class));
            ((Activity) context).finish();
        }
    }

    public List<DataModel> readData(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userLogged", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("dataUser", null);
        if (json != null){
            Type type = new TypeToken<List<DataModel>>(){
            }.getType();
            dataUser = gson.fromJson(json, type);
        }

        return dataUser;
    }

    public void logout(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userLogged", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void insertToMySQL(String email, String phone, String fullname, String photo){
        APIRequestData apiRequestData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> process = apiRequestData.insert(email, phone, fullname, photo);
        process.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body() != null) {
                    readPersonalInfo(email);
                    Toast.makeText(context, "Message 1:"+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Something went wrong"+response.errorBody(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(context, "Errorrrrrr: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void readPersonalInfo(String email){
        APIRequestData apiRequestData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> process = apiRequestData.getPersonalInfo(email);
        process.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body() != null){
                    dataUser = response.body().getData();
                    saveUser(dataUser);
                    Toast.makeText(context, "message: "+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(context, "Message: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void signUpUser(String fullname, String email, String phone, String password){
        APIRequestData apiRequestData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> process = apiRequestData.ardSignUp(fullname, email, phone, password);
        process.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body() != null){
                    if (response.body().getData() != null){
                        Intent intent = new Intent(context, OneTimePassword.class);
                        intent.putExtra("email", response.body().getData().get(0).getEmail());
                        context.startActivity(intent);

//                    context.startActivity(new Intent(context, OneTimePassword.class));
                        ((Activity) context).finish();
                    }
                    Toast.makeText(context, "message: "+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "message: something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(context, "Message: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void verifEmail(String email, String otp){
        APIRequestData apiRequestData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> process = apiRequestData.verifEmail(email, otp);
        process.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body() != null){
                    Toast.makeText(context, "Code Sent", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Code tidak valid", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(context, "Message: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateVerifEmail(String email){
        logout();
        APIRequestData apiRequestData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> process = apiRequestData.updateVerifEmail(email);
        process.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                dataUser = response.body().getData();
                saveUser(dataUser);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(context, "erot: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
