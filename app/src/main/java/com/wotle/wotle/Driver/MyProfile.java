package com.wotle.wotle.Driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.wotle.wotle.API.PrefManager;
import com.wotle.wotle.R;

public class MyProfile extends AppCompatActivity {

    private PrefManager pref;

    private LinearLayout backProfileDriverEquipment;

    private ImageView myProfileDriver, backProfileDriverMyProfile;
    private EditText edUnameDriver, edNo_hpDriver, edEmailDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        pref = new PrefManager(this);
        myProfileDriver = findViewById(R.id.myProfileDriver);
        backProfileDriverMyProfile =findViewById(R.id.backProfileDriverMyProfile);
        backProfileDriverEquipment = findViewById(R.id.backProfileDriverEquipment);
        edUnameDriver = findViewById(R.id.edUnameDriver);
        edNo_hpDriver = findViewById(R.id.edNo_hpDriver);
        edEmailDriver = findViewById(R.id.edEmailDriver);

        Glide.with(this).load(pref.readData().get(0).getFoto_profile()).circleCrop().into(myProfileDriver);
        edUnameDriver.setText(pref.readData().get(0).getNama_lengkap());
        edNo_hpDriver.setText(pref.readData().get(0).getNo_hp());
        edEmailDriver.setText(pref.readData().get(0).getEmail());

        backProfileDriverMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyProfile.this, SettingsDriver.class));
                finish();
            }
        });

        backProfileDriverEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyProfile.this, EquipmentDriver.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, SettingsDriver.class));
        finish();
    }
}