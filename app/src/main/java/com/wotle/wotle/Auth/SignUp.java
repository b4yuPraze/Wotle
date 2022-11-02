package com.wotle.wotle.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wotle.wotle.API.PrefManager;
import com.wotle.wotle.R;

public class SignUp extends AppCompatActivity {

    private TextView signIn;
    private EditText fullname, email, phone, password, confPassword;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initEditText();
        register.setEnabled(false);
        listenerTextWatcher();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidPassword(password.getText().toString(), confPassword.getText().toString())){
                        new PrefManager(SignUp.this).signUpUser(fullname.getText().toString(), email.getText().toString(), phone.getText().toString(), password.getText().toString());
//                        Toast.makeText(SignUp.this, "Kami telah mengirim email verifikasi ke: "+email.getText().toString(), Toast.LENGTH_SHORT).show();
                }else{
                    confPassword.setError("Password atau Konfirmasi Password tidak cocok");
                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, Login.class));
                finish();
            }
        });
    }

    private void initEditText(){
        signIn = findViewById(R.id.LoginAkun);
        fullname = findViewById(R.id.fullnameSingup);
        email = findViewById(R.id.EmailSingup);
        phone = findViewById(R.id.phoneSignUp);
        password = findViewById(R.id.PasswordSignup);
        confPassword = findViewById(R.id.ConfirmSingup);
        register = findViewById(R.id.btnRegister);
    }

    private TextWatcher onOffButton = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            register.setEnabled(checkIsEmpty(fullname.getText().toString(), email.getText().toString(), phone.getText().toString(), password.getText().toString(), confPassword.getText().toString()));
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private Boolean checkIsEmpty(String fullname, String email, String phone, String password, String confPassword){
        if (fullname.isEmpty() && email.isEmpty() && phone.isEmpty() && password.isEmpty() && confPassword.isEmpty()){
            this.fullname.setError("Nama Lengkap tidak boleh kosong");
            this.email.setError("Email tidak boleh kosong");
            this.phone.setError("Nomor Telepon tidak boleh kosong");
            this.password.setError("Password tidak boleh kosong");
            this.confPassword.setError("Konfirmasi password tidak boleh kosong");
            return false;
        }else if(!checkValidEmail(email)){
            this.email.setError("Email tidak valid");
            return false;
        }else if(phone.length() < 9){
            this.phone.setError("Nomer Telepon tidak valid");
            return false;
        }else if(phone.length() > 13){
            this.phone.setError("Nomer Telepon tidak valid");
            return false;
        }else if(password.length()<8){
            this.password.setError("Password minimal harus 8 karakter");
            return false;
        }else{
            return true;
        }

    }

    private void listenerTextWatcher(){
        fullname.addTextChangedListener(onOffButton);
        email.addTextChangedListener(onOffButton);
        phone.addTextChangedListener(onOffButton);
        password.addTextChangedListener(onOffButton);
        confPassword.addTextChangedListener(onOffButton);
    }

    private Boolean checkValidPassword(String password, String confPassword){
        if (password.equals(confPassword)){
            return true;
        }else{
            return false;
        }
    }

    private Boolean checkValidEmail(String email){
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }else{
            return false;
        }
    }
}