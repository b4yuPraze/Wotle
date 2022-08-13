package com.wotle.wonderfulshuttle.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wotle.wonderfulshuttle.API.APIRequestData;
import com.wotle.wonderfulshuttle.API.RetroServer;
import com.wotle.wonderfulshuttle.Model.DataModel;
import com.wotle.wonderfulshuttle.Model.ReponseModel;
import com.wotle.wonderfulshuttle.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity{

    private EditText email, password;
    private Button btnLogin, btnLoginGoogle, btnLoginPhone;
    private List<DataModel> dataLogin;

    private String TempEmail, TempPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.EmailSignin);
        password = findViewById(R.id.PasswordSignin);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == btnLogin){
                    TempEmail = email.getText().toString();
                    TempPass = password.getText().toString();
                    if (TempEmail.equals("") && TempPass.equals("")){
                        email.setError("Kolom E-Mail tidak boleh kosong");
                        password.setError("Kolom Password tidak boleh");
                    }else{
                        login();
                    }
                }
            }
        });
    }

    private void login(){
        APIRequestData ardLogin = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ReponseModel> processLogin = ardLogin.ardSignin(TempEmail, TempPass);

        processLogin.enqueue(new Callback<ReponseModel>() {
            @Override
            public void onResponse(Call<ReponseModel> call, Response<ReponseModel> response) {
                if (response.body().getMessage() == null){
                    Toast.makeText(SignIn.this, "Data Response Null", Toast.LENGTH_SHORT).show();
                }else{
                    String message = response.body().getMessage();
                    dataLogin = response.body().getData();
                    if (message.equals("Success")){
                        Toast.makeText(SignIn.this, "Login Berhasil: "+message+" Data: "+dataLogin.get(0).getEmail()+" - "+dataLogin.get(0).getPassword(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReponseModel> call, Throwable t) {
                Toast.makeText(SignIn.this, "Email atau password salah!", Toast.LENGTH_LONG).show();
            }
        });
    }
}