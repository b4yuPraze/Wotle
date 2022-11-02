package com.wotle.wotle.Auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wotle.wotle.API.APIRequestData;
import com.wotle.wotle.API.PrefManager;
import com.wotle.wotle.API.RetroServer;
import com.wotle.wotle.Customer.Customer;
import com.wotle.wotle.Driver.Driver;
import com.wotle.wotle.Model.DataModel;
import com.wotle.wotle.Model.ResponseModel;
import com.wotle.wotle.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText email, pass;
    private TextView signUp;
    private Button btnLogin;
    private SignInButton btnGoogle;
    private ProgressBar progressBar;

    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;

    private List<DataModel> dataLogin;

    int ALL_PERMISSION = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoogle = findViewById(R.id.btnGoogle);
        email = findViewById(R.id.tvEmail);
        pass = findViewById(R.id.tvPassword);
        signUp = findViewById(R.id.SignupAkun);
        progressBar = findViewById(R.id.progressButtonLogin);

        checkUser();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("34896984956-7g6k4kf8s93d03qr7t5roeqejho1549l.apps.googleusercontent.com").requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(Login.this, googleSignInOptions);
        firebaseAuth = FirebaseAuth.getInstance();
        initButton();
    }

    private void initButton(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressButton progressButton = new ProgressButton(Login.this, progressBar, btnLogin);
                progressButton.buttonActivated();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (checkValidEmail(email.getText().toString())){
                            checkSelfPermission();
                        }else{
                            progressButton.buttonInActive();
                            Handler handler1 = new Handler();
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    email.setError("E-Mail tidak valid");
                                }
                            }, 500);
                        }
                    }
                }, 3000);
            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, 100);
                checkPermission();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SignUp.class));
                finish();
            }
        });
    }



    private void checkUser(){
        if (new PrefManager(Login.this).readData() != null){
            if (new PrefManager(Login.this).readData().get(0).getRole().equals("Customer")){
                startActivity(new Intent(Login.this, Customer.class));
                finish();
            }else{
                startActivity(new Intent(Login.this, Driver.class));
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()) {
                try {
                    GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);
                    if (googleSignInAccount != null) {
                        AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    firebaseAuth = FirebaseAuth.getInstance();
                                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                                    if (firebaseUser != null){
                                        for (UserInfo profile : firebaseUser.getProviderData()){
                                            String provider = profile.getProviderId();
                                            if (provider.equals("google.com")){
                                                String phone_number = null;
                                                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                                                if (ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                                                    return;
                                                }
                                                phone_number = telephonyManager.getLine1Number();
                                                String email = firebaseUser.getEmail();
                                                String fullname = firebaseUser.getDisplayName();
                                                String photo = firebaseUser.getPhotoUrl().toString();
                                                new PrefManager(Login.this).insertToMySQL(email, phone_number, fullname, photo);
                                            }
                                        }
                                    }

                                }else{
                                    Toast.makeText(Login.this, "Messagessss: "+task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }catch (ApiException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void actLogin(String s1, String s2){
        if(checkNotEmptyField(s1, s2)){
            APIRequestData apiRequestData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> process = apiRequestData.ardSignin(s1, s2);
            process.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if (response.body() != null){
                        String message = response.body().getMessage();
                        if (message.equals("Success")){
                            dataLogin = response.body().getData();
//                            String role = dataLogin.get(0).getRole();
//                            String email_verif = dataLogin.get(0).getEmail_verif();
//                            checkRoleUser(role, email_verif);
                            checkPermission();
                            new PrefManager(Login.this).saveUser(dataLogin);
                        }else if (message.equals("Failure")){
                            Toast.makeText(Login.this, "E-Mail atau Password salah!", Toast.LENGTH_SHORT).show();
                            ProgressButton progressButton = new ProgressButton(Login.this, progressBar, btnLogin);
                            progressButton.buttonInActive();
                        }
                    }else{
                        Toast.makeText(Login.this, "E-Mail belum terdaftar, silahkan daftar.", Toast.LENGTH_SHORT).show();
                        ProgressButton progressButton = new ProgressButton(Login.this, progressBar, btnLogin);
                        progressButton.buttonInActive();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(Login.this, "Message On FAil: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean checkNotEmptyField(String s1, String s2){
        if (s1.isEmpty() == true && s2.isEmpty() == true){
            email.setError("E-Mail harus diisi!");
            return false;
        }

        if (s2.isEmpty()){
            pass.setError("Password harus diisi");
            ProgressButton progressButton = new ProgressButton(Login.this, progressBar, btnLogin);
            progressButton.buttonInActive();
            return false;
        }
        return true;
    }

    private void checkRoleUser(String role, String email_verif){
//        if (role.equals("Customer") && email_verif.equals("valid")){
//            new PrefManager(this).saveUser(dataLogin);
//            email.setText("");
//            pass.setText("");
//            startActivity(new Intent(this, Customer.class));
//            finish();
//        }else if (role.equals("Driver") && email_verif.equals("valid")){
//            new PrefManager(this).saveUser(dataLogin);
//            email.setText("");
//            pass.setText("");
//            startActivity(new Intent(this, Driver.class));
//            finish();
//        }else{
//            Toast.makeText(this, "Silahkan verifikasi E-Mail terlebih dahulu!", Toast.LENGTH_SHORT).show();
//        }

    }

    private Boolean checkValidEmail(String email){
        if (email.isEmpty() == false && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }else{
            return false;
        }
    }

    private void checkPermission(){
        final String[] permissions = new String[]{
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_PHONE_NUMBERS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_DENIED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_DENIED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSION);
        }
        else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkSelfPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        }else{
            requestRequirementPermission();
        }
    }

    private void requestRequirementPermission(){
        final String[] permissions = new String[]{
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_PHONE_NUMBERS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
        };

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, String.valueOf(permissions))){

            new AlertDialog.Builder(this).setTitle("Permission needed")
                    .setMessage("This permission is needed if you dont allow you cant open")
                    .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(Login.this, permissions, ALL_PERMISSION);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();

        }else{
            ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == ALL_PERMISSION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                String mail = email.getText().toString();
                String pasw = pass.getText().toString();
                actLogin(mail, pasw);
            }else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}