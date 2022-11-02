package com.wotle.wotle.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wotle.wotle.API.PrefManager;
import com.wotle.wotle.Model.DataModel;
import com.wotle.wotle.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Edit_Account extends AppCompatActivity {

    private PrefManager pref;

    private ImageView iev;
    private EditText edNamaLengkap, edNo_hp, edAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        pref = new PrefManager(this);

        iev = findViewById(R.id.imageEditProfil);
        edNamaLengkap = findViewById(R.id.edNamaLengkap);
        edNo_hp = findViewById(R.id.edNo_hp);
        edAlamat = findViewById(R.id.edAlamat);

        edNamaLengkap.setText(pref.readData().get(0).getNama_lengkap());
        edNo_hp.setText(pref.readData().get(0).getNo_hp());
        edAlamat.setText(pref.readData().get(0).getAlamat());
        Glide.with(this).load(pref.readData().get(0).getFoto_profile()).circleCrop().into(iev);

//        checkUserLogged();

    }


//    private void checkUserLogged() {
//        readPreferences();
//
//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        if (dataUser != null){
//            edNamaLengkap.setText(dataUser.get(0).getNama_lengkap());
//            Glide.with(this).load(dataUser.get(0).getFoto_profile()).circleCrop().into(iev);
//            if (dataUser.get(0).getNo_hp() != null || dataUser.get(0).getNo_hp().equals("null")){
//                edNo_hp.setText(dataUser.get(0).getNo_hp());
//            }else{
//                edNo_hp.setText(telephonyManager.getLine1Number());
//            }
//            edAlamat.setText(dataUser.get(0).getAlamat());
//        }
//    }

//    private void readPreferences() {
//        SharedPreferences sharedPreferences = getSharedPreferences("userLogged", MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("dataUser", null);
//        Type type = new TypeToken<List<DataModel>>() {
//        }.getType();
//        dataUser = gson.fromJson(json, type);
//        if (dataUser == null) {
//            dataUser = new ArrayList<>();
//        }
//    }

}